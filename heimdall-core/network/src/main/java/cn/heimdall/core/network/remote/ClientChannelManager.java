package cn.heimdall.core.network.remote;

import cn.heimdall.core.cluster.ClusterInfoManager;
import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.config.ConfigurationFactory;
import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.message.NodeRole;
import cn.heimdall.core.utils.common.CollectionUtil;
import cn.heimdall.core.utils.exception.NetworkException;
import io.netty.channel.Channel;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * netty client channel的管理
 */
public class ClientChannelManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientChannelManager.class);

    private final ConcurrentMap<String, Object> channelLocks = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, ClientPoolKey> poolKeyMap = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Channel> channels = new ConcurrentHashMap<>();

    private final GenericKeyedObjectPool<ClientPoolKey, Channel> nettyClientKeyPool;

    private Function<String, ClientPoolKey> poolKeyFunction;

    public ClientChannelManager(final NettyKeyPoolFactory nettyKeyPoolFactory,
                                final Function<String, ClientPoolKey> poolKeyFunction,
                                final NetworkConfig clientConfig) {
        nettyClientKeyPool = new GenericKeyedObjectPool<>(nettyKeyPoolFactory);
        nettyClientKeyPool.setConfig(wrapNettyPoolConfig(clientConfig));
        this.poolKeyFunction = poolKeyFunction;
    }

    /**
     * 封装对象池配置
     * @param clientConfig
     * @return
     */
    private GenericKeyedObjectPoolConfig wrapNettyPoolConfig(final NetworkConfig clientConfig) {
        //TODO 对象池配置
        GenericKeyedObjectPoolConfig objectPoolConfig = new GenericKeyedObjectPoolConfig();
        //objectPoolConfig.setMaxTotal(clientConfig.getMaxPoolActive());
       // objectPoolConfig.setMinIdlePerKey(clientConfig.getMinPoolIdle());
        objectPoolConfig.setMaxWaitMillis(clientConfig.getMaxAcquireConnMills());
        objectPoolConfig.setTestOnBorrow(clientConfig.isPoolTestBorrow());
        objectPoolConfig.setTestOnReturn(clientConfig.isPoolTestReturn());
        objectPoolConfig.setLifo(clientConfig.isPoolLifo());
        return objectPoolConfig;
    }


    ConcurrentMap<String, Channel> getChannels() {
        return channels;
    }


    Channel acquireChannel(String serverAddress) {
        Channel channelToServer = channels.get(serverAddress);
        if (channelToServer != null) {
            channelToServer = getExistAliveChannel(channelToServer, serverAddress);
            if (channelToServer != null) {
                return channelToServer;
            }
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("will connect to {}", serverAddress);
        }
        Object lockObj = CollectionUtil.computeIfAbsent(channelLocks, serverAddress, key -> new Object());
        synchronized (lockObj) {
            return doConnect(serverAddress);
        }
    }

    void releaseChannel(Channel channel, String serverAddress) {
        if (channel == null || serverAddress == null) { return; }
        try {
            synchronized (channelLocks.get(serverAddress)) {
                Channel ch = channels.get(serverAddress);
                if (ch == null) {
                    nettyClientKeyPool.returnObject(poolKeyMap.get(serverAddress), channel);
                    return;
                }
                if (ch.compareTo(channel) == 0) {
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("return to pool, rm channel:{}", channel);
                    }
                    destroyChannel(serverAddress, channel);
                } else {
                    nettyClientKeyPool.returnObject(poolKeyMap.get(serverAddress), channel);
                }
            }
        } catch (Exception exx) {
            LOGGER.error(exx.getMessage());
        }
    }

    void destroyChannel(String serverAddress, Channel channel) {
        if (channel == null) { return; }
        try {
            if (channel.equals(channels.get(serverAddress))) {
                channels.remove(serverAddress);
            }
            nettyClientKeyPool.returnObject(poolKeyMap.get(serverAddress), channel);
        } catch (Exception exx) {
            LOGGER.error("return channel to rmPool error:{}", exx.getMessage());
        }
    }

    void reconnect(Set<InetSocketAddress> availList) {
        if (CollectionUtil.isEmpty(availList)) {
            LOGGER.warn("ClientChannelManager reconnect availList is null");
            return;
        }
        for (InetSocketAddress serverAddress : availList) {
            try {
                acquireChannel(serverAddress.getHostString());
            } catch (Exception e) {
                LOGGER.error("reconnect error, ", e);
            }
        }
    }

    void invalidateObject(final String serverAddress, final Channel channel) throws Exception {
        nettyClientKeyPool.invalidateObject(poolKeyMap.get(serverAddress), channel);
    }

    public void registerChannel(final String serverAddress, final Channel channel) {
        Channel channelToServer = channels.get(serverAddress);
        if (channelToServer != null && channelToServer.isActive()) {
            return;
        }
        channels.put(serverAddress, channel);
    }

    private Channel doConnect(String serverAddress) {
        Channel channelToServer = channels.get(serverAddress);
        if (channelToServer != null && channelToServer.isActive()) {
            return channelToServer;
        }
        Channel channelFromPool;
        try {
            ClientPoolKey currentPoolKey = poolKeyFunction.apply(serverAddress);
            poolKeyMap.putIfAbsent(serverAddress, currentPoolKey);
            channelFromPool = nettyClientKeyPool.borrowObject(poolKeyMap.get(serverAddress));
            channels.put(serverAddress, channelFromPool);
        } catch (Exception e) {
            LOGGER.error("ClientChannelManager doConnect is error {}", serverAddress, e);
            throw new NetworkException("can not register RM,err:" + e.getMessage());
        }
        return channelFromPool;
    }


    private Channel getExistAliveChannel(Channel rmChannel, String serverAddress) {
        return null;
    }

}
