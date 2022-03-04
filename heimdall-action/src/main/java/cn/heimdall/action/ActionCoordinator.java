package cn.heimdall.action;

import cn.heimdall.action.processor.QueryProcessor;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageDoorway;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.ActionMessageRequest;
import cn.heimdall.core.message.body.MessageRequest;
import cn.heimdall.core.message.body.action.QueryAppStateRequest;
import cn.heimdall.core.message.body.action.QueryAppStateResponse;
import cn.heimdall.core.message.body.action.QueryMetricRequest;
import cn.heimdall.core.message.body.action.QueryMetricResponse;
import cn.heimdall.core.message.body.action.QueryTraceRequest;
import cn.heimdall.core.message.body.action.QueryTraceResponse;
import cn.heimdall.core.message.hander.ActionInboundHandler;
import cn.heimdall.core.network.coordinator.Coordinator;
import cn.heimdall.core.network.remote.AbstractRemotingServer;
import cn.heimdall.core.network.remote.RemotingInstanceFactory;
import cn.heimdall.core.utils.annotation.LoadLevel;
import cn.heimdall.core.utils.constants.LoadLevelConstants;
import cn.heimdall.core.utils.enums.NettyServerType;
import cn.heimdall.core.utils.spi.Initialize;

/**
 * 处理和修改配置和查询的协调器
 */
@LoadLevel(name = LoadLevelConstants.COORDINATOR_ACTION)
public class ActionCoordinator implements MessageDoorway, Coordinator, ActionInboundHandler, Initialize {

    @Override
    public NettyServerType getNettyServerType() {
        return NettyServerType.ACTION;
    }

    @Override
    public AbstractRemotingServer generateServerRemoteInstance() {
        AbstractRemotingServer remotingServer = RemotingInstanceFactory.generateRemotingServer(getNettyServerType());
        QueryProcessor queryProcessor = new QueryProcessor(this, remotingServer);
        remotingServer.doRegisterProcessor(MessageType.ACTION_QUERY_METRIC_REQUEST, queryProcessor);
        remotingServer.doRegisterProcessor(MessageType.ACTION_QUERY_TRANCE_LOG_REQUEST, queryProcessor);
        remotingServer.doRegisterProcessor(MessageType.ACTION_QUERY_APP_STATE_REQUEST, queryProcessor);
        return remotingServer;
    }

    @Override
    public void initClientRemoteInstance() {
        //TODO donothing
    }

    @Override
    public void init() {
        //TODO donothing
    }

    @Override
    public MessageBody onRequest(MessageBody request) {
        //如果不是发往服务端的消息
        if (!(request instanceof MessageRequest)) {
            throw new IllegalArgumentException();
        }
        ActionMessageRequest actionMessageRequest = (QueryMetricRequest) request;
        actionMessageRequest.setActionInboundHandler(this);
        //执行inbound的方法
        return actionMessageRequest.handle();
    }

    @Override
    public void onResponse(MessageBody response) {
        return;
    }


    @Override
    public QueryMetricResponse handle(QueryMetricRequest request) {
        return null;
    }

    @Override
    public QueryTraceResponse handle(QueryTraceRequest request) {
        return null;
    }

    @Override
    public QueryAppStateResponse handle(QueryAppStateRequest request) {
        return null;
    }
}
