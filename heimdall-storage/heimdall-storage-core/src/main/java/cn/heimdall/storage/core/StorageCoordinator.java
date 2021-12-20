package cn.heimdall.storage.core;

import cn.heimdall.core.config.constants.ConfigurationKeys;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.origin.AppStateRequest;
import cn.heimdall.core.message.body.origin.AppStateResponse;
import cn.heimdall.core.message.body.origin.MessageTreeRequest;
import cn.heimdall.core.message.body.origin.MessageTreeResponse;
import cn.heimdall.core.message.hander.ComputeInboundHandler;
import cn.heimdall.core.network.coordinator.Coordinator;
import cn.heimdall.core.network.remote.AbstractRemotingServer;
import cn.heimdall.core.utils.annotation.LoadLevel;
import cn.heimdall.core.utils.constants.LoadLevelConstants;
import cn.heimdall.core.utils.enums.NettyServerType;
import cn.heimdall.storage.core.processor.server.StoreAppStateProcessor;
import cn.heimdall.storage.core.processor.server.StoreMetricProcessor;
import cn.heimdall.storage.core.processor.server.StoreTraceLogProcessor;

@LoadLevel(name = LoadLevelConstants.STORAGE_COORDINATOR)
public class StorageCoordinator implements ComputeInboundHandler, Coordinator {
    @Override
    public AppStateResponse handle(AppStateRequest request) {
        return null;
    }

    @Override
    public MessageTreeResponse handle(MessageTreeRequest request) {
        return null;
    }

    @Override
    public NettyServerType getNettyServerType() {
        return NettyServerType.TRANSPORT;
    }

    @Override
    public void doRegisterProcessor(AbstractRemotingServer remotingServer) {
        remotingServer.doRegisterProcessor(MessageType.TYPE_STORE_APP_STATE_REQUEST, new StoreAppStateProcessor(remotingServer));
        remotingServer.doRegisterProcessor(MessageType.TYPE_STORE_METRIC_REQUEST, new StoreMetricProcessor());
        remotingServer.doRegisterProcessor(MessageType.TYPE_STORE_TRANCE_LOG_REQUEST, new StoreTraceLogProcessor());
    }
}
