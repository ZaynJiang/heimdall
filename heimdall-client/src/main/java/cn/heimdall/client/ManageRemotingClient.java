package cn.heimdall.client;

import cn.heimdall.client.processor.HeartbeatResponseProcessor;
import cn.heimdall.client.processor.RegisterResponseProcessor;
import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.config.NetworkManageConfig;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.NodeRole;
import cn.heimdall.core.network.remote.AbstractRemotingClient;
import cn.heimdall.core.network.remote.ClientPoolKey;
import cn.heimdall.core.utils.thread.NamedThreadFactory;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

/**
 * 管理类消息客户端
 */
public class ManageRemotingClient extends AbstractRemotingClient {

    private static volatile ManageRemotingClient instance;

    private final AtomicBoolean initialized = new AtomicBoolean(false);
    //自身的角色
    private List<NodeRole> selfRoles;
    //该客户端远程请求的角色（管理类是向guarder请求）
    private NodeRole remoteRole = NodeRole.GUARDER;

    private ClientInfo clientInfo;

    public ManageRemotingClient(NetworkConfig networkConfig, ThreadPoolExecutor executor) {
        //TODO
        super(networkConfig, executor, null);
    }

    @Override
    public void init() {
        this.selfRoles = ClientInfoManager.getInstance().getNodeRoles();
        if (initialized.compareAndSet(false, true)) {
            super.init();
            this.registerProcessor();
        }
    }

    @Override
    protected Set<InetSocketAddress> getAvailableAddress() {
        return null;
    }

    @Override
    protected long getResourceExpireTime() {
        //TODO 目前5分钟之内的心跳都算有效
        return 5 * 1000L;
    }

    @Override
    protected NodeRole getRemoteRole() {
        return this.remoteRole;
    }

    private void registerProcessor() {
        super.registerProcessor(MessageType.TYPE_NODE_REGISTER_REQUEST, new RegisterResponseProcessor(), messageExecutor);
        super.registerProcessor(MessageType.TYPE_NODE_HEARTBEAT_REQUEST, new HeartbeatResponseProcessor(), messageExecutor);
    }

    private static final long KEEP_ALIVE_TIME = Integer.MAX_VALUE;

    private static final int MAX_QUEUE_SIZE = 20000;

    public static ManageRemotingClient getInstance() {
        if (instance == null) {
            synchronized (ManageRemotingClient.class) {
                if (instance == null) {
                    NetworkManageConfig networkManageConfig = new NetworkManageConfig();
                    //发送消息线程池
                    final ThreadPoolExecutor messageExecutor = new ThreadPoolExecutor(
                            NetworkManageConfig.MANAGE_WORK_THREAD_SIZE, NetworkManageConfig.MANAGE_WORK_THREAD_SIZE,
                            KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(MAX_QUEUE_SIZE),
                            new NamedThreadFactory("app-manage-remoting-client:", true),
                            new ThreadPoolExecutor.CallerRunsPolicy());
                    instance = new ManageRemotingClient(networkManageConfig,  messageExecutor);
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
        return addressIp -> new ClientPoolKey(selfRoles, addressIp,null);
    }

    @Override
    public void onRegisterMsgSuccess(String serverAddress, Channel channel) {

    }

    @Override
    public void onRegisterMsgFail(String serverAddress, Channel channel) {

    }
}
