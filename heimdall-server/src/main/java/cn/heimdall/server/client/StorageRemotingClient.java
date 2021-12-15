package cn.heimdall.server.client;

import cn.heimdall.core.cluster.NodeInfo;
import cn.heimdall.core.cluster.NodeInfoManager;
import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.config.NetworkManageConfig;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.utils.enums.NodeRole;
import cn.heimdall.core.network.processor.client.NodeHeartbeatProcessor;
import cn.heimdall.core.network.remote.AbstractRemotingClient;
import cn.heimdall.core.network.remote.ClientPoolKey;
import cn.heimdall.core.utils.thread.NamedThreadFactory;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 请求到storage的client
 */
public class StorageRemotingClient extends AbstractRemotingClient {

    private static volatile StorageRemotingClient instance;

    private NodeInfo nodeInfo;

    public StorageRemotingClient(NetworkConfig networkConfig, ThreadPoolExecutor executor) {
        //TODO
        super(networkConfig, executor, null);
    }

    @Override
    public void init() {
        nodeInfo = NodeInfoManager.getInstance().getNodeInfo();
        super.init();
        //TODO 自身的一些初始化
    }

    @Override
    protected Set<InetSocketAddress> getAvailableAddress() {
        return null;
    }

    @Override
    protected long getResourceExpireTime() {
        return 0;
    }

    @Override
    protected NodeRole getRemoteRole() {
        return null;
    }

    private void registerProcessor() {
        NodeHeartbeatProcessor nodeHeartbeatProcessor = new NodeHeartbeatProcessor();
        super.registerProcessor(MessageType.TYPE_STORE_TRANCE_LOG_REQUEST, nodeHeartbeatProcessor, messageExecutor);
        super.registerProcessor(MessageType.TYPE_STORE_METRIC_REQUEST, nodeHeartbeatProcessor, messageExecutor);
        super.registerProcessor(MessageType.TYPE_STORE_APP_STATE_REQUEST, nodeHeartbeatProcessor, messageExecutor);
    }


    private static final long KEEP_ALIVE_TIME = Integer.MAX_VALUE;
    private static final int MAX_QUEUE_SIZE = 20000;

    public static StorageRemotingClient getInstance() {
        if (instance == null) {
            synchronized (StorageRemotingClient.class) {
                if (instance == null) {
                    NetworkManageConfig networkManageConfig = new NetworkManageConfig();
                    final ThreadPoolExecutor messageExecutor = new ThreadPoolExecutor(
                            NetworkManageConfig.MANAGE_WORK_THREAD_SIZE, NetworkManageConfig.MANAGE_WORK_THREAD_SIZE,
                            KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(MAX_QUEUE_SIZE),
                            new NamedThreadFactory("manage:", true),
                            new ThreadPoolExecutor.CallerRunsPolicy());
                    instance = new StorageRemotingClient(networkManageConfig, messageExecutor);
                }
            }
        }
        return instance;
    }

    @Override
    public String loadBalance() {
        return null;
    }

    @Override
    protected Function<String, ClientPoolKey> getPoolKeyFunction() {
        return addressIp -> new ClientPoolKey(nodeInfo.getNodeRoles(), addressIp, null);
    }

    @Override
    protected boolean isRegisterSuccess(MessageBody body) {
        return false;
    }

    @Override
    public void onRegisterMsgSuccess(String serverAddress, Channel channel, Object request, Object response){

    }

    @Override
    public void onRegisterMsgFail(String serverAddress, Channel channel, Object request, Object response) {

    }
}