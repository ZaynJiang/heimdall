package cn.heimdall.server.server;

import cn.heimdall.core.cluster.NodeInfo;
import cn.heimdall.core.cluster.NodeInfoManager;
import cn.heimdall.core.config.NetworkManageConfig;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.network.processor.server.ServerIdleProcessor;
import cn.heimdall.core.network.remote.AbstractRemotingServer;
import cn.heimdall.core.utils.thread.NamedThreadFactory;
import cn.heimdall.guarder.GuarderCoordinator;
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
public final class ManageRemotingServer extends AbstractRemotingServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManageRemotingServer.class);

    private final AtomicBoolean initialized = new AtomicBoolean(false);

    private static volatile ManageRemotingServer INSTANCE;

    private NodeInfo nodeInfo;

    public ManageRemotingServer(ThreadPoolExecutor executor, NetworkManageConfig manageConfig) {
        super(executor, manageConfig);
    }

    @Override
    public void init() {
        nodeInfo = NodeInfoManager.getInstance().getNodeInfo();
        // registry processor
        registerProcessor();
        if (initialized.compareAndSet(false, true)) {
            super.init();
        }
    }


    private void registerProcessor() {
        //如果是guarder
        if (nodeInfo.isGuarder()) {
            final GuarderCoordinator coordinator = new GuarderCoordinator();
            coordinator.init();
            super.registerProcessor(MessageType.TYPE_NODE_REGISTER_REQUEST, new RegisterRequestProcessor(this, coordinator), messageExecutor);
            super.registerProcessor(MessageType.TYPE_NODE_HEARTBEAT_REQUEST, new HeartbeatRequestProcessor(this, coordinator), messageExecutor);
            super.registerProcessor(MessageType.TYPE_CLIENT_HEARTBEAT_REQUEST, new HeartbeatRequestProcessor(this, coordinator), messageExecutor);
            super.registerProcessor(MessageType.TYPE_CLIENT_REGISTER_REQUEST, new RegisterRequestProcessor(this, coordinator), messageExecutor);

            //注册
            super.registerProcessor(MessageType.TYPE_PING_MESSAGE, new ServerIdleProcessor(this), messageExecutor);
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
