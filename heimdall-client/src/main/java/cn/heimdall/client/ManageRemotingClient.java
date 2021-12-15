package cn.heimdall.client;

import cn.heimdall.client.processor.HeartbeatResponseProcessor;
import cn.heimdall.client.processor.RegisterResponseProcessor;
import cn.heimdall.core.cluster.ClusterInfo;
import cn.heimdall.core.cluster.ClusterInfoManager;
import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.config.ConfigurationFactory;
import cn.heimdall.core.config.HeimdallConfig;
import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.config.NetworkManageConfig;
import cn.heimdall.core.config.constants.ConfigurationKeys;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.utils.enums.NodeRole;
import cn.heimdall.core.message.ResultCode;
import cn.heimdall.core.message.body.register.ClientRegisterRequest;
import cn.heimdall.core.message.body.register.ClientRegisterResponse;
import cn.heimdall.core.network.processor.client.ClientIdleProcessor;
import cn.heimdall.core.network.remote.AbstractRemotingClient;
import cn.heimdall.core.network.remote.ClientPoolKey;
import cn.heimdall.core.utils.common.CollectionUtil;
import cn.heimdall.core.utils.common.NetUtil;
import cn.heimdall.core.utils.exception.NetworkException;
import cn.heimdall.core.utils.thread.NamedThreadFactory;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 管理类消息客户端
 */
public class ManageRemotingClient extends AbstractRemotingClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManageRemotingClient.class);

    private static volatile ManageRemotingClient instance;

    private final AtomicBoolean initialized = new AtomicBoolean(false);
    //自身的角色
    private List<NodeRole> selfRoles;
    //该客户端远程请求的角色（管理类是向guarder请求）
    private NodeRole remoteRole = NodeRole.GUARDER;
    private ClusterInfo clusterInfo;
    private Configuration configuration;
    private Set<InetSocketAddress> seekAddresses;
    private ClientInfo clientInfo;

    public ManageRemotingClient(NetworkConfig networkConfig, ThreadPoolExecutor executor) {
        //TODO
        super(networkConfig, executor, null);
    }

    @Override
    public void init() {
        this.selfRoles = ClientInfoManager.getInstance().getNodeRoles();
        this.clusterInfo = ClusterInfoManager.getInstance().getClusterInfo();
        this.configuration = ConfigurationFactory.getInstance();
        this.clientInfo = ClientInfoManager.getInstance().getClientInfo();
        if (initialized.compareAndSet(false, true)) {
            super.init();
            this.registerProcessor();
            String[] ips = this.configuration.getConfigFromSys(ConfigurationKeys.GUARDER_SEED_HOSTS).split(",");
            this.seekAddresses = Stream.of(ips).
                    map(ip -> NetUtil.toInetSocketAddress(ip)).collect(Collectors.toSet());
        }
    }

    @Override
    protected Set<InetSocketAddress> getAvailableAddress() {
        //如果动态变更信息中没有有效数据
        Set<InetSocketAddress> addresses = this.clusterInfo.
                getActiveInetSocketAddress(NodeRole.GUARDER, HeimdallConfig.NODE_HEART_BEAT_EXPIRE_TIME);
        if (!CollectionUtil.isEmpty(addresses)) {
            return addresses;
        }
        return this.seekAddresses;
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
        super.registerProcessor(MessageType.TYPE_CLIENT_HEARTBEAT_RESPONSE, new HeartbeatResponseProcessor(getFutures()), messageExecutor);
        super.registerProcessor(MessageType.TYPE_CLIENT_REGISTER_RESPONSE, new RegisterResponseProcessor(getFutures()), messageExecutor);
        super.registerProcessor(MessageType.TYPE_PING_MESSAGE, new ClientIdleProcessor());
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
                    instance = new ManageRemotingClient(networkManageConfig, messageExecutor);
                    instance.init();
                }
            }
        }
        return instance;
    }

    @Override
    public String loadBalance() {
        //TODO 负载均衡
        Set<InetSocketAddress> addresses = getAvailableAddress();
        ArrayList<InetSocketAddress> ipArrayList = new ArrayList<>();
        ipArrayList.addAll(addresses);
        //循环随机数
        Random random = new Random();
        //随机数在list数量中取（1-list.size）
        int pos = random.nextInt(ipArrayList.size());
        InetSocketAddress serverNameReturn = ipArrayList.get(pos);
        return NetUtil.toStringAddress(serverNameReturn);
    }

    @Override
    protected Function<String, ClientPoolKey> getPoolKeyFunction() {
        return addressIp -> new ClientPoolKey(selfRoles, addressIp, new ClientRegisterRequest(clientInfo.getAppName(), clientInfo.getHost()));
    }

    @Override
    protected boolean isRegisterSuccess(MessageBody body) {
        ClientRegisterResponse clientRegisterResponse = (ClientRegisterResponse) body;
        return clientRegisterResponse.getResultCode() == ResultCode.SUCCESS.getCode();
    }

    @Override
    public void onRegisterMsgSuccess(String serverAddress, Channel channel, Object request, Object response) {
        MessageBody responseBody = (MessageBody) response;
        MessageBody requestBody = (MessageBody) request;
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("register client manager success. client version:{}, server version:{},channel:{}", requestBody,
                    responseBody, channel);
        }
        getClientChannelManager().registerChannel(serverAddress, channel);
    }

    @Override
    public void onRegisterMsgFail(String serverAddress, Channel channel, Object request, Object response) {
        MessageBody responseBody = (MessageBody) response;
        MessageBody requestBody = (MessageBody) request;
        String errMsg = String.format(
                "register client manager failed. client version: %s, errorMsg: %s, " + "channel: %s", requestBody,
                responseBody, channel);
        throw new NetworkException(errMsg);
    }
}
