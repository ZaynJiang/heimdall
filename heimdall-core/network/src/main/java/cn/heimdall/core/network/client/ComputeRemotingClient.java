package cn.heimdall.core.network.client;

import cn.heimdall.core.cluster.NodeInfo;
import cn.heimdall.core.cluster.NodeInfoManager;
import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.config.NetworkManageConfig;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.network.processor.ClientProcessor;
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
 * 数据类消息客户端
 */
public class ComputeRemotingClient extends AbstractRemotingClient {

    private static volatile ComputeRemotingClient instance;

    private NodeInfo nodeInfo;

    public ComputeRemotingClient(NetworkConfig networkConfig, ThreadPoolExecutor executor) {
        //TODO
        super(networkConfig, executor, null);
    }

    @Override
    public void init() {
        nodeInfo = NodeInfoManager.getInstance().getNodeInfo();
        super.init();
    }

    @Override
    public void doRegisterProcessor(MessageType messageType, ClientProcessor clientProcessor) {
        super.registerProcessor(messageType, clientProcessor, messageExecutor);
    }

    @Override
    protected Set<InetSocketAddress> getAvailableAddress() {
        return null;
    }

    @Override
    protected long getResourceExpireTime() {
        return 0;
    }

    private static final long KEEP_ALIVE_TIME = Integer.MAX_VALUE;

    private static final int MAX_QUEUE_SIZE = 20000;

    public static ComputeRemotingClient getInstance() {
        if (instance == null) {
            synchronized (ComputeRemotingClient.class) {
                if (instance == null) {
                    NetworkManageConfig networkManageConfig = new NetworkManageConfig();
                    //发送消息线程池
                    final ThreadPoolExecutor messageExecutor = new ThreadPoolExecutor(
                            NetworkManageConfig.MANAGE_WORK_THREAD_SIZE, NetworkManageConfig.MANAGE_WORK_THREAD_SIZE,
                            KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(MAX_QUEUE_SIZE),
                            new NamedThreadFactory("manage:", true),
                            new ThreadPoolExecutor.CallerRunsPolicy());
                    instance = new ComputeRemotingClient(networkManageConfig,  messageExecutor);
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
        return addressIp -> new ClientPoolKey(nodeInfo.getNodeRoles(), addressIp,null);
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