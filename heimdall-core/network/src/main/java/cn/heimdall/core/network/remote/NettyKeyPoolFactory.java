
package cn.heimdall.core.network.remote;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.RpcMessage;
import cn.heimdall.core.network.bootstrap.NettyClientBootstrap;
import cn.heimdall.core.utils.common.NetUtil;
import cn.heimdall.core.utils.exception.NetworkException;
import io.netty.channel.Channel;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * 对象池工厂
 */
public class NettyKeyPoolFactory implements KeyedPooledObjectFactory<ClientPoolKey, Channel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyKeyPoolFactory.class);

    private final AbstractRemotingClient remotingClient;

    private final NettyClientBootstrap clientBootstrap;

    public NettyKeyPoolFactory(AbstractRemotingClient rpcRemotingClient, NettyClientBootstrap clientBootstrap) {
        this.remotingClient = rpcRemotingClient;
        this.clientBootstrap = clientBootstrap;
    }

    @Override
    public PooledObject<Channel> makeObject(ClientPoolKey key) throws Exception {
        InetSocketAddress address = NetUtil.toInetSocketAddress(key.getAddress());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("NettyPool create channel to " + key);
        }
        Channel tmpChannel = clientBootstrap.getNewChannel(address);
        long start = System.currentTimeMillis();
        MessageBody response;
        Channel channelToServer = null;
        if (key.getMessage() == null) {
            throw new NetworkException("register msg is null, role:" + key.getNodeRoles());
        }
        try {
            //发去注册信息
            response = (MessageBody) remotingClient.sendSyncRequest(tmpChannel, new RpcMessage(key.getMessage()));
            if (!remotingClient.isRegisterSuccess(response)) {
                remotingClient.onRegisterMsgFail(key.getAddress(), tmpChannel,  key.getMessage(), response);
            } else {
                channelToServer = tmpChannel;
                remotingClient.onRegisterMsgSuccess(key.getAddress(), tmpChannel,  key.getMessage(), response);
            }
        } catch (Exception e) {
            if (tmpChannel != null) {
                tmpChannel.close();
            }
            throw new NetworkException("register " + key + " error, errMsg:" + e.getMessage());
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("register success, cost " + (System.currentTimeMillis() - start), key + " ,role:" + " ,channel:" + channelToServer);
        }
        return new DefaultPooledObject<>(channelToServer);

    }

    @Override
    public void destroyObject(ClientPoolKey clientPoolKey, PooledObject<Channel> pooledObject) throws Exception {
        if (pooledObject != null && pooledObject.getObject() != null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("will destroy channel:" +  pooledObject.getObject());
            }
            pooledObject.getObject().disconnect();
            pooledObject.getObject().close();
        }
    }

    @Override
    public boolean validateObject(ClientPoolKey clientPoolKey, PooledObject<Channel> pooledObject) {
        if (pooledObject != null && pooledObject.getObject().isActive()) {
            return true;
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("channel valid false,channel:" + pooledObject.getObject());
        }
        return false;
    }

    @Override
    public void activateObject(ClientPoolKey clientPoolKey, PooledObject<Channel> pooledObject) throws Exception {

    }

    @Override
    public void passivateObject(ClientPoolKey clientPoolKey, PooledObject<Channel> pooledObject) throws Exception {

    }

}
