package cn.heimdall.server.server;

import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.config.NetworkTransportConfig;
import cn.heimdall.core.network.remote.AbstractRemotingServer;
import cn.heimdall.core.utils.thread.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 查询类服务
 */
public class ActionRemotingServer extends AbstractRemotingServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionRemotingServer.class);

    private final AtomicBoolean initialized = new AtomicBoolean(false);

    private static volatile ActionRemotingServer INSTANCE;

    public ActionRemotingServer(ThreadPoolExecutor executor, NetworkConfig networkConfig) {
        super(executor, networkConfig);
    }

    @Override
    public void init() {
        super.init();
    }

    public static ActionRemotingServer getInstance() {
        if (INSTANCE == null) {
            synchronized (TransportRemotingServer.class) {
                if (INSTANCE == null) {
                    final NetworkTransportConfig networkTransportConfig = new NetworkTransportConfig();
                    final ThreadPoolExecutor workingThreads = new ThreadPoolExecutor(networkTransportConfig.getMinServerPoolSize(),
                            networkTransportConfig.getMaxServerPoolSize(), networkTransportConfig.getKeepAliveTime(), TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(networkTransportConfig.getMaxTaskQueueSize()),
                            new NamedThreadFactory("ActionRemotingServer", networkTransportConfig.getMaxServerPoolSize()),
                            new ThreadPoolExecutor.CallerRunsPolicy());
                    INSTANCE = new ActionRemotingServer(workingThreads, networkTransportConfig);
                }
            }
        }
        return INSTANCE;
    }
}
