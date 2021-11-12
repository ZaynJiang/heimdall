package cn.heimdall.server.network;

import cn.heimdall.compute.processor.server.AppStateProcessor;
import cn.heimdall.compute.processor.server.MessageTreeProcessor;
import cn.heimdall.core.config.ConfigurationFactory;
import cn.heimdall.core.config.NetworkManageConfig;
import cn.heimdall.core.config.constants.ConfigurationKeys;
import cn.heimdall.core.message.MessageType;

import cn.heimdall.core.network.remote.AbstractRemotingServer;
import cn.heimdall.guarder.processor.server.HeartbeatRequestProcessor;
import cn.heimdall.guarder.processor.server.RegisterRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;
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
            super.registerProcessor(MessageType.TYPE_COMPUTE_REGISTER.getTypeCode(), new RegisterRequestProcessor(), messageExecutor);
            super.registerProcessor(MessageType.TYPE_COMPUTE_HEARTBEAT.getTypeCode(), new HeartbeatRequestProcessor(), messageExecutor);
        }
        //如果是compute
        if (computeRole) {
            super.registerProcessor(MessageType.TYPE_CLIENT_APP_STATE.getTypeCode(), new AppStateProcessor(), messageExecutor);
            super.registerProcessor(MessageType.TYPE_CLIENT_MESSAGE_TREE.getTypeCode(), new MessageTreeProcessor(), messageExecutor);
        }
        //如果是storage
        if (storageRole) {

        }
    }

    public static ManageRemotingServer getInstance() {
        if (INSTANCE == null) {
            synchronized (ManageRemotingServer.class) {
                if (INSTANCE == null) {
                /*    final ThreadPoolExecutor workingThreads = new ThreadPoolExecutor(NetworkManageConfig.getMinServerPoolSize(),
                            NetworkManageConfig.getMaxServerPoolSize(), NetworkConfig.getKeepAliveTime(), TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(NetworkManageConfig.getMaxTaskQueueSize()),
                            new NamedThreadFactory("ServerHandlerThread", NetworkManageConfig.getMaxServerPoolSize()), new ThreadPoolExecutor.CallerRunsPolicy());*/
                    INSTANCE = new ManageRemotingServer(null, null);
                }
            }
        }
        return INSTANCE;
    }

}
