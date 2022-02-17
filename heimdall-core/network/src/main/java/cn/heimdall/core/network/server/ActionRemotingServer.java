package cn.heimdall.core.network.server;

import cn.heimdall.core.config.NetworkActionConfig;
import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.network.processor.ServerProcessor;
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
    public void doRegisterProcessor(MessageType messageType, ServerProcessor serverProcessor) {
        super.registerProcessor(messageType, serverProcessor);
    }

    @Override
    public void init() {
        if (!initialized.compareAndSet(false, true)) {
            LOGGER.warn("actionRemotingServer has bean init");
            return;
        }
        super.init();
    }

    public static ActionRemotingServer getInstance() {
        if (INSTANCE == null) {
            synchronized (TransportRemotingServer.class) {
                if (INSTANCE == null) {
                    final NetworkActionConfig networkTransportConfig = new NetworkActionConfig();
                    final ThreadPoolExecutor workingThreads = new ThreadPoolExecutor(networkTransportConfig.getMinServerPoolSize(),
                            networkTransportConfig.getMaxServerPoolSize(), networkTransportConfig.getKeepAliveTime(), TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(networkTransportConfig.getMaxTaskQueueSize()),
                            new NamedThreadFactory("ActionRemotingServer", networkTransportConfig.getMaxServerPoolSize()),
                            new ThreadPoolExecutor.CallerRunsPolicy());
                    INSTANCE = new ActionRemotingServer(workingThreads, networkTransportConfig);
                    INSTANCE.setListenPort(networkTransportConfig.getPort());
                    INSTANCE.init();
                }
            }
        }
        return INSTANCE;
    }
}
