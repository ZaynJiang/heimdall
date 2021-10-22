package cn.heimdall.core.network.remote;

import cn.heimdall.core.config.NetworkManageConfig;
import cn.heimdall.core.network.bootstrap.ManageClientBootstrap;
import cn.heimdall.core.network.bootstrap.RemotingBootstrap;
import cn.heimdall.core.utils.thread.NamedThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ManageClient extends AbstractRemotingClient{

    private static volatile ManageClient instance;

    public ManageClient(RemotingBootstrap clientBootstrap, ThreadPoolExecutor executor) {
        super(clientBootstrap, executor);
    }

    @Override
    public void init() {
        super.init();
        //TODO 自身的一些初始化
    }

    private static final long KEEP_ALIVE_TIME = Integer.MAX_VALUE;
    private static final int MAX_QUEUE_SIZE = 20000;

    public static ManageClient getInstance() {
        if (instance == null) {
            synchronized (ManageClient.class) {
                if (instance == null) {
                    final ThreadPoolExecutor messageExecutor = new ThreadPoolExecutor(
                            NetworkManageConfig.MANAGE_WORK_THREAD_SIZE, NetworkManageConfig.MANAGE_WORK_THREAD_SIZE,
                            KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(MAX_QUEUE_SIZE),
                            new NamedThreadFactory("manage:", true),
                            new ThreadPoolExecutor.CallerRunsPolicy());
                    instance = new ManageClient(new ManageClientBootstrap(), messageExecutor);
                }
            }
        }
        return instance;
    }
}
