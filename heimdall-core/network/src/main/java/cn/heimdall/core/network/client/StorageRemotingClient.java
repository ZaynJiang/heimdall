package cn.heimdall.core.network.client;

import cn.heimdall.core.cluster.ClusterInfo;
import cn.heimdall.core.cluster.ClusterInfoManager;
import cn.heimdall.core.cluster.NodeInfo;
import cn.heimdall.core.cluster.NodeInfoManager;
import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.config.ConfigurationFactory;
import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.config.NetworkManageConfig;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.network.processor.ClientProcessor;
import cn.heimdall.core.network.remote.AbstractRemotingClient;
import cn.heimdall.core.network.remote.ClientPoolKey;
import cn.heimdall.core.utils.common.NetUtil;
import cn.heimdall.core.utils.constants.ConfigurationKeys;
import cn.heimdall.core.utils.thread.NamedThreadFactory;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StorageRemotingClient extends AbstractRemotingClient {
    private static volatile StorageRemotingClient instance;

    private final AtomicBoolean initialized = new AtomicBoolean(false);
    private NodeInfo nodeInfo;
    private ClusterInfo clusterInfo;
    private Configuration configuration;
    private Set<InetSocketAddress> seekAddresses;

    public StorageRemotingClient(NetworkConfig networkConfig, ThreadPoolExecutor executor) {
        //TODO
        super(networkConfig, executor, null);
    }

    @Override
    public void init() {
        this.clusterInfo = ClusterInfoManager.getInstance().getClusterInfo();
        this.nodeInfo = NodeInfoManager.getInstance().getNodeInfo();
        this.configuration = ConfigurationFactory.getInstance();
        if (initialized.compareAndSet(false, true)) {
            super.init();
            String[] ips = this.configuration.getConfigFromSys(ConfigurationKeys.COMPUTE_HOSTS).split(",");
            this.seekAddresses = Stream.of(ips).
                    map(ip -> NetUtil.toInetSocketAddress(ip)).collect(Collectors.toSet());
        }
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
    public void doRegisterProcessor(MessageType messageType, ClientProcessor clientProcessor) {
        registerProcessor(messageType, clientProcessor);
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
                            new NamedThreadFactory("StorageRemotingClient:", true),
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
