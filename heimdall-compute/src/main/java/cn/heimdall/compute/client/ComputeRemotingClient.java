package cn.heimdall.compute;

import cn.heimdall.core.config.NetworkManageConfig;
import cn.heimdall.core.network.remote.AbstractRemotingClient;
import cn.heimdall.core.utils.thread.NamedThreadFactory;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ComputeRemotingClient extends AbstractRemotingClient {

    private static volatile ComputeRemotingClient instance;

    public ComputeRemotingClient(ThreadPoolExecutor executor) {
        //TODO
        super();
    }

    @Override
    public void init() {
        super.init();
        //TODO 自身的一些初始化
    }

    private static final long KEEP_ALIVE_TIME = Integer.MAX_VALUE;
    private static final int MAX_QUEUE_SIZE = 20000;

    public static ComputeRemotingClient getInstance() {
        if (instance == null) {
            synchronized (ComputeRemotingClient.class) {
                if (instance == null) {
                    final ThreadPoolExecutor messageExecutor = new ThreadPoolExecutor(
                            NetworkManageConfig.MANAGE_WORK_THREAD_SIZE, NetworkManageConfig.MANAGE_WORK_THREAD_SIZE,
                            KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(MAX_QUEUE_SIZE),
                            new NamedThreadFactory("manage:", true),
                            new ThreadPoolExecutor.CallerRunsPolicy());
                    instance = new ComputeRemotingClient(messageExecutor);
                }
            }
        }
        return instance;
    }

    @Override
    public String loadBalance() {
        return null;
    }

}
