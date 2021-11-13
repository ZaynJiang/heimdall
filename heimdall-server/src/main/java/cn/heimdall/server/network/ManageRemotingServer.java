package cn.heimdall.server.network;

import cn.heimdall.core.config.ConfigurationFactory;
import cn.heimdall.core.config.NetworkManageConfig;
import cn.heimdall.core.config.constants.ConfigurationKeys;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.network.remote.AbstractRemotingServer;
import cn.heimdall.core.utils.thread.NamedThreadFactory;
import cn.heimdall.guarder.processor.server.HeartbeatRequestProcessor;
import cn.heimdall.guarder.processor.server.RegisterRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 管理类server
 */
public class ManageRemotingServer extends AbstractRemotingServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManageRemotingServer.class);

    private final AtomicBoolean initialized = new AtomicBoolean(false);

    private static volatile ManageRemotingServer INSTANCE;

    @Override
    public void init() {
        // registry processor
        registerProcessor();
        if (initialized.compareAndSet(false, true)) {
            super.init();
        }
    }

    public ManageRemotingServer(ThreadPoolExecutor executor, NetworkManageConfig manageConfig) {
        super(executor, manageConfig);
    }

    private void registerProcessor() {
        boolean guarderRole = ConfigurationFactory.getInstance().getBoolean(ConfigurationKeys.NODE_GUARDER, true);
        boolean computeRole = ConfigurationFactory.getInstance().getBoolean(ConfigurationKeys.NODE_COMPUTE, true);
        boolean storageRole = ConfigurationFactory.getInstance().getBoolean(ConfigurationKeys.NODE_STORAGE, true);
        //如果是guarder
        if (guarderRole) {
            super.registerProcessor(MessageType.TYPE_NODE_REGISTER.getTypeCode(), new RegisterRequestProcessor(), messageExecutor);
            super.registerProcessor(MessageType.TYPE_NODE_HEARTBEAT.getTypeCode(), new HeartbeatRequestProcessor(), messageExecutor);
        }
    }

    public static ManageRemotingServer getInstance() {
        if (INSTANCE == null) {
            synchronized (ManageRemotingServer.class) {
                if (INSTANCE == null) {
                    final NetworkManageConfig networkManageConfig = new NetworkManageConfig();
                    final ThreadPoolExecutor workingThreads = new ThreadPoolExecutor(networkManageConfig.getMinServerPoolSize(),
                            networkManageConfig.getMaxServerPoolSize(), networkManageConfig.getKeepAliveTime(), TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(networkManageConfig.getMaxTaskQueueSize()),
                            new NamedThreadFactory("ManageServerHandlerThread", networkManageConfig.getMaxServerPoolSize()), new ThreadPoolExecutor.CallerRunsPolicy());
                    INSTANCE = new ManageRemotingServer(workingThreads, networkManageConfig);
                }
            }
        }
        return INSTANCE;
    }

}
