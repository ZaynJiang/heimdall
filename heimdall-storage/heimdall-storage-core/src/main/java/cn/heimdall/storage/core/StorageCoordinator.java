package cn.heimdall.storage.core;

import cn.heimdall.core.cluster.ClusterInfo;
import cn.heimdall.core.cluster.ClusterInfoManager;
import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.config.ConfigurationFactory;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageDoorway;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.store.StoreAppStateRequest;
import cn.heimdall.core.message.body.store.StoreAppStateResponse;
import cn.heimdall.core.message.body.store.StoreMetricRequest;
import cn.heimdall.core.message.body.store.StoreMetricResponse;
import cn.heimdall.core.message.body.store.StoreTraceRequest;
import cn.heimdall.core.message.body.store.StoreTraceResponse;
import cn.heimdall.core.message.body.store.search.SearchAppStateRequest;
import cn.heimdall.core.message.body.store.search.SearchAppStateResponse;
import cn.heimdall.core.message.body.store.search.SearchMetricRequest;
import cn.heimdall.core.message.body.store.search.SearchMetricResponse;
import cn.heimdall.core.message.body.store.search.SearchTraceRequest;
import cn.heimdall.core.message.body.store.search.SearchTraceResponse;
import cn.heimdall.core.message.hander.StoreInboundHandler;
import cn.heimdall.core.network.client.GuarderRemotingClient;
import cn.heimdall.core.network.coordinator.Coordinator;
import cn.heimdall.core.network.processor.client.NodeHeartbeatResponseProcessor;
import cn.heimdall.core.network.processor.client.NodeRegisterResponseProcessor;
import cn.heimdall.core.network.remote.AbstractRemotingServer;
import cn.heimdall.core.network.remote.RemotingInstanceFactory;
import cn.heimdall.core.utils.annotation.LoadLevel;
import cn.heimdall.core.utils.constants.LoadLevelConstants;
import cn.heimdall.core.utils.enums.NettyServerType;
import cn.heimdall.core.utils.spi.EnhancedServiceLoader;
import cn.heimdall.core.utils.spi.Initialize;
import cn.heimdall.storage.core.processor.server.StoreAppStateProcessor;
import cn.heimdall.storage.core.processor.server.StoreCommonProcessor;
import cn.heimdall.storage.core.processor.server.StoreMetricProcessor;
import cn.heimdall.storage.core.processor.server.StoreTraceLogProcessor;

import static cn.heimdall.core.utils.constants.ConfigurationKeys.STORAGE_TYPE;

@LoadLevel(name = LoadLevelConstants.COORDINATOR_STORAGE)
public class StorageCoordinator implements StoreInboundHandler, Coordinator, MessageDoorway, Initialize {

    private StoreManager storeManager;

    private Configuration configuration;

    private ClusterInfo clusterInfo;

    @Override
    public void init() {
        clusterInfo = ClusterInfoManager.getInstance().getClusterInfo();
        configuration = ConfigurationFactory.getInstance();
        storeManager =  EnhancedServiceLoader.load(StoreManager.class,
                configuration.getConfig(STORAGE_TYPE, LoadLevelConstants.STORE_MANAGER_LUCENE));
    }

    @Override
    public NettyServerType getNettyServerType() {
        return NettyServerType.TRANSPORT;
    }

    @Override
    public AbstractRemotingServer generateServerRemoteInstance() {
        AbstractRemotingServer remotingServer = RemotingInstanceFactory.generateRemotingServer(getNettyServerType());
        remotingServer.doRegisterProcessor(MessageType.STORE_APP_STATE_REQUEST, new StoreCommonProcessor(this, remotingServer));
        remotingServer.doRegisterProcessor(MessageType.STORE_METRIC_REQUEST, new StoreCommonProcessor(this, remotingServer));
        remotingServer.doRegisterProcessor(MessageType.STORE_TRANCE_LOG_REQUEST, new StoreCommonProcessor(this, remotingServer));
        return remotingServer;
    }

    @Override
    public void initClientRemoteInstance() {
        //init guarder client
        GuarderRemotingClient guarder = GuarderRemotingClient.getInstance();
        guarder.doRegisterProcessor(MessageType.NODE_HEARTBEAT_REQUEST,  new NodeHeartbeatResponseProcessor());
        guarder.doRegisterProcessor(MessageType.NODE_REGISTER_REQUEST, new NodeRegisterResponseProcessor());
    }

    @Override
    public MessageBody onRequest(MessageBody request) {
        return null;
    }

    @Override
    public void onResponse(MessageBody response) {

    }

    @Override
    public StoreMetricResponse handle(StoreMetricRequest request) {
        return storeManager.handle(request);
    }

    @Override
    public StoreTraceResponse handle(StoreTraceRequest request) {
        return storeManager.handle(request);
    }

    @Override
    public StoreAppStateResponse handle(StoreAppStateRequest request) {
        return storeManager.handle(request);
    }

    @Override
    public SearchAppStateResponse handle(SearchAppStateRequest request) {
        return storeManager.handle(request);
    }

    @Override
    public SearchTraceResponse handle(SearchTraceRequest request) {
        return storeManager.handle(request);
    }

    @Override
    public SearchMetricResponse handle(SearchMetricRequest request) {
        return storeManager.handle(request);
    }

}
