package cn.heimdall.server.network;

import cn.heimdall.compute.processor.server.AppStateProcessor;
import cn.heimdall.compute.processor.server.MessageTreeProcessor;
import cn.heimdall.core.config.ConfigurationFactory;
import cn.heimdall.core.config.NetworkManageConfig;
import cn.heimdall.core.config.constants.ConfigurationKeys;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.network.remote.AbstractRemotingServer;
import cn.heimdall.storage.processor.server.StoreAppStateProcessor;
import cn.heimdall.storage.processor.server.StoreMetricProcessor;
import cn.heimdall.storage.processor.server.StoreTraceLogProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

public class TransportRemotingServer extends AbstractRemotingServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManageRemotingServer.class);

    private final AtomicBoolean initialized = new AtomicBoolean(false);

    private static volatile TransportRemotingServer INSTANCE;

    @Override
    public void init() {
        // registry processor
        registerProcessor();
        if (initialized.compareAndSet(false, true)) {
            super.init();
        }
    }

    public TransportRemotingServer(ThreadPoolExecutor executor, NetworkManageConfig manageConfig) {
        super(executor, manageConfig);
    }

    private void registerProcessor() {
        boolean computeRole = ConfigurationFactory.getInstance().getBoolean(ConfigurationKeys.NODE_COMPUTE, true);
        boolean storageRole = ConfigurationFactory.getInstance().getBoolean(ConfigurationKeys.NODE_STORAGE, true);
        //如果是compute
        if (computeRole) {
            super.registerProcessor(MessageType.TYPE_CLIENT_APP_STATE.getTypeCode(), new AppStateProcessor(), messageExecutor);
            super.registerProcessor(MessageType.TYPE_CLIENT_MESSAGE_TREE.getTypeCode(), new MessageTreeProcessor(), messageExecutor);
        }
        //如果是storage
        if (storageRole) {
            super.registerProcessor(MessageType.TYPE_COMPUTE_STORE_METRIC.getTypeCode(), new StoreMetricProcessor(), messageExecutor);
            super.registerProcessor(MessageType.TYPE_COMPUTE_STORE_APP_STATE.getTypeCode(), new StoreAppStateProcessor(), messageExecutor);
            super.registerProcessor(MessageType.TYPE_COMPUTE_STORE_TRANCE_LOG.getTypeCode(), new StoreTraceLogProcessor(), messageExecutor);
        }
    }

    public static TransportRemotingServer getInstance() {
        if (INSTANCE == null) {
            synchronized (TransportRemotingServer.class) {
                if (INSTANCE == null) {
                /*    final ThreadPoolExecutor workingThreads = new ThreadPoolExecutor(NetworkManageConfig.getMinServerPoolSize(),
                            NetworkManageConfig.getMaxServerPoolSize(), NetworkConfig.getKeepAliveTime(), TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(NetworkManageConfig.getMaxTaskQueueSize()),
                            new NamedThreadFactory("ServerHandlerThread", NetworkManageConfig.getMaxServerPoolSize()), new ThreadPoolExecutor.CallerRunsPolicy());*/
                    INSTANCE = new TransportRemotingServer(null, null);
                }
            }
        }
        return INSTANCE;
    }
}