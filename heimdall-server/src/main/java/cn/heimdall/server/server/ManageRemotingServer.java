package cn.heimdall.server.server;

import cn.heimdall.core.cluster.NodeInfo;
import cn.heimdall.core.cluster.NodeInfoManager;
import cn.heimdall.core.config.NetworkManageConfig;
import cn.heimdall.core.config.constants.ConfigurationKeys;
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
    public void doRegisterProcessor(MessageType messageType, ServerProcessor serverProcessor) {
        registerProcessor(messageType, serverProcessor);
    }

    @Override
    public void init() {
        if (!initialized.compareAndSet(false, true)) {
            LOGGER.warn("manageRemotingServer has bean init");
            return;
        }
        nodeInfo = NodeInfoManager.getInstance().getNodeInfo();
        super.init();
    }

    public static ManageRemotingServer getInstance() {
        if (INSTANCE == null) {
            synchronized (ManageRemotingServer.class) {
                if (INSTANCE == null) {
                    final NetworkManageConfig networkManageConfig = new NetworkManageConfig();
                    final ThreadPoolExecutor workingThreads = new ThreadPoolExecutor(networkManageConfig.getMinServerPoolSize(),
                            networkManageConfig.getMaxServerPoolSize(), networkManageConfig.getKeepAliveTime(), TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(networkManageConfig.getMaxTaskQueueSize()),
                            new NamedThreadFactory("ManageRemotingServer", networkManageConfig.getMaxServerPoolSize()), new ThreadPoolExecutor.CallerRunsPolicy());
                    INSTANCE = new ManageRemotingServer(workingThreads, networkManageConfig);
                    INSTANCE.setListenPort(networkManageConfig.getPort());
                    INSTANCE.init();
                }
            }
        }
        return INSTANCE;
    }

}
