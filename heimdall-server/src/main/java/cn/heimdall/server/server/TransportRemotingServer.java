package cn.heimdall.server.server;

import cn.heimdall.compute.ComputeCoordinator;
import cn.heimdall.compute.processor.server.AppStateProcessor;
import cn.heimdall.compute.processor.server.MessageTreeProcessor;
import cn.heimdall.core.cluster.NodeInfo;
import cn.heimdall.core.cluster.NodeInfoManager;
import cn.heimdall.core.config.NetworkTransportConfig;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.network.coordinator.Coordinator;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.AbstractRemotingServer;
import cn.heimdall.core.utils.thread.NamedThreadFactory;
import cn.heimdall.storage.core.processor.server.StoreAppStateProcessor;
import cn.heimdall.storage.core.processor.server.StoreMetricProcessor;
import cn.heimdall.storage.core.processor.server.StoreTraceLogProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 业务数据类server
 */
public final class TransportRemotingServer extends AbstractRemotingServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManageRemotingServer.class);

    private final AtomicBoolean initialized = new AtomicBoolean(false);

    private static volatile TransportRemotingServer INSTANCE;

    private NodeInfo nodeInfo;

    @Override
    public void init() {
        if (!initialized.compareAndSet(false, true)) {
            LOGGER.warn("TransportRemotingServer has bean init");
            return;
        }
        nodeInfo = NodeInfoManager.getInstance().getNodeInfo();
        // registry processor
        super.init();
    }

    public TransportRemotingServer(ThreadPoolExecutor executor, NetworkTransportConfig transportConfig) {
        super(executor, transportConfig);
    }

    @Override
    public void doRegisterProcessor(MessageType messageType, ServerProcessor serverProcessor) {
        super.registerProcessor(messageType, serverProcessor);
    /*    //如果是compute
        if (nodeInfo.isCompute()) {
            ComputeCoordinator computeCoordinator = new ComputeCoordinator(this);
            //应用状态上报server-processor
            super.registerProcessor(MessageType.TYPE_CLIENT_APP_STATE_REQUEST, new AppStateProcessor(this, computeCoordinator));
            //消息树上报server-processor
            super.registerProcessor(MessageType.TYPE_CLIENT_MESSAGE_TREE_REQUEST, new MessageTreeProcessor(this, computeCoordinator));
        }
        //如果是存储器
        if (nodeInfo.isStorage()) {
            //应用状态上报processor
            super.registerProcessor(MessageType.TYPE_STORE_APP_STATE_REQUEST, new StoreAppStateProcessor(this));
            //消息树上报processor
            super.registerProcessor(MessageType.TYPE_STORE_METRIC_REQUEST, new StoreMetricProcessor());
            super.registerProcessor(MessageType.TYPE_STORE_TRANCE_LOG_REQUEST, new StoreTraceLogProcessor());
        }*/

    }

    public static TransportRemotingServer getInstance() {
        if (INSTANCE == null) {
            synchronized (TransportRemotingServer.class) {
                if (INSTANCE == null) {
                    final NetworkTransportConfig networkTransportConfig = new NetworkTransportConfig();
                    final ThreadPoolExecutor workingThreads = new ThreadPoolExecutor(networkTransportConfig.getMinServerPoolSize(),
                            networkTransportConfig.getMaxServerPoolSize(), networkTransportConfig.getKeepAliveTime(), TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(networkTransportConfig.getMaxTaskQueueSize()),
                            new NamedThreadFactory("TransportRemotingServer", networkTransportConfig.getMaxServerPoolSize()),
                            new ThreadPoolExecutor.CallerRunsPolicy());
                    INSTANCE = new TransportRemotingServer(workingThreads, networkTransportConfig);
                    INSTANCE.setListenPort(networkTransportConfig.getPort());
                    INSTANCE.init();
                }
            }
        }
        return INSTANCE;
    }
}