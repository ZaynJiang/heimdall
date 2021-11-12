package cn.heimdall.compute.client;

import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.config.NetworkManageConfig;
import cn.heimdall.core.config.constants.ClientRole;
import cn.heimdall.core.network.remote.ClientChannelManager;
import cn.heimdall.core.network.remote.AbstractRemotingClient;
import cn.heimdall.core.network.remote.ClientPoolKey;
import cn.heimdall.core.utils.thread.NamedThreadFactory;
import io.netty.channel.Channel;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class RemotingStorageClient extends AbstractRemotingClient {

    private static volatile RemotingStorageClient instance;

    public RemotingStorageClient(NetworkConfig networkConfig, ClientChannelManager clientChannelManager, ThreadPoolExecutor executor) {
        //TODO
        super(networkConfig, clientChannelManager, null, executor);
    }

    @Override
    public void init() {
        super.init();
        //TODO 自身的一些初始化
    }

    private static final long KEEP_ALIVE_TIME = Integer.MAX_VALUE;
    private static final int MAX_QUEUE_SIZE = 20000;

    public static RemotingStorageClient getInstance() {
        if (instance == null) {
            synchronized (RemotingStorageClient.class) {
                if (instance == null) {
                    NetworkManageConfig networkManageConfig = new NetworkManageConfig();
                    final ThreadPoolExecutor messageExecutor = new ThreadPoolExecutor(
                            NetworkManageConfig.MANAGE_WORK_THREAD_SIZE, NetworkManageConfig.MANAGE_WORK_THREAD_SIZE,
                            KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(MAX_QUEUE_SIZE),
                            new NamedThreadFactory("manage:", true),
                            new ThreadPoolExecutor.CallerRunsPolicy());
                    instance = new RemotingStorageClient(networkManageConfig, null, messageExecutor);
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
        return addressIp -> new ClientPoolKey(ClientRole.STORAGE, addressIp,null);
    }

    @Override
    public void onRegisterMsgSuccess(String serverAddress, Channel channel) {

    }

    @Override
    public void onRegisterMsgFail(String serverAddress, Channel channel) {

    }
}
