package cn.heimdall.server.network;

import cn.heimdall.compute.processor.server.AppStateProcessor;
import cn.heimdall.compute.processor.server.MessageTreeProcessor;
import cn.heimdall.core.cluster.NodeInfo;
import cn.heimdall.core.cluster.NodeInfoManager;
import cn.heimdall.core.config.NetworkTransportConfig;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.network.remote.AbstractRemotingServer;
import cn.heimdall.core.utils.thread.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class TransportRemotingServer extends AbstractRemotingServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManageRemotingServer.class);

    private final AtomicBoolean initialized = new AtomicBoolean(false);

    private static volatile TransportRemotingServer INSTANCE;

    private NodeInfo nodeInfo;

    @Override
    public void init() {
        nodeInfo = NodeInfoManager.getInstance().getNodeInfo();
        // registry processor
        registerProcessor();
        if (initialized.compareAndSet(false, true)) {
            super.init();
        }
    }

    public TransportRemotingServer(ThreadPoolExecutor executor, NetworkTransportConfig transportConfig) {
        super(executor, transportConfig);
    }

    private void registerProcessor() {
        //如果是compute
        if (nodeInfo.isGuarder()) {
            //应用状态上报processor
            super.registerProcessor(MessageType.TYPE_CLIENT_APP_STATE.getTypeCode(), new AppStateProcessor(), messageExecutor);
            //消息树上报processor
            super.registerProcessor(MessageType.TYPE_CLIENT_MESSAGE_TREE.getTypeCode(), new MessageTreeProcessor(), messageExecutor);
        }
        //如果是存储器
        if (nodeInfo.isStorage()) {
            //应用状态上报processor
            super.registerProcessor(MessageType.TYPE_COMPUTE_STORE_APP_STATE.getTypeCode(), new AppStateProcessor(), messageExecutor);
            //消息树上报processor
            super.registerProcessor(MessageType.TYPE_COMPUTE_STORE_METRIC.getTypeCode(), new MessageTreeProcessor(), messageExecutor);
            super.registerProcessor(MessageType.TYPE_COMPUTE_STORE_TRANCE_LOG.getTypeCode(), new MessageTreeProcessor(), messageExecutor);
        }

    }

    public static TransportRemotingServer getInstance() {
        if (INSTANCE == null) {
            synchronized (TransportRemotingServer.class) {
                if (INSTANCE == null) {
                    final NetworkTransportConfig networkTransportConfig = new NetworkTransportConfig();
                    final ThreadPoolExecutor workingThreads = new ThreadPoolExecutor(networkTransportConfig.getMinServerPoolSize(),
                            networkTransportConfig.getMaxServerPoolSize(), networkTransportConfig.getKeepAliveTime(), TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(networkTransportConfig.getMaxTaskQueueSize()),
                            new NamedThreadFactory("ServerHandlerThread", networkTransportConfig.getMaxServerPoolSize()),
                            new ThreadPoolExecutor.CallerRunsPolicy());
                    INSTANCE = new TransportRemotingServer(workingThreads, networkTransportConfig);
                }
            }
        }
        return INSTANCE;
    }
}