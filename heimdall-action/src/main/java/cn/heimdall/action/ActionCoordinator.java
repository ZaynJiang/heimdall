package cn.heimdall.action;

import cn.heimdall.action.processor.QueryMetricProcessor;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageDoorway;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.MessageRequest;
import cn.heimdall.core.message.body.action.QueryMetricRequest;
import cn.heimdall.core.message.body.action.QueryMetricResponse;
import cn.heimdall.core.message.hander.ActionInboundHandler;
import cn.heimdall.core.network.coordinator.Coordinator;
import cn.heimdall.core.network.remote.AbstractRemotingServer;
import cn.heimdall.core.network.remote.RemotingInstanceFactory;
import cn.heimdall.core.utils.annotation.LoadLevel;
import cn.heimdall.core.utils.constants.LoadLevelConstants;
import cn.heimdall.core.utils.enums.NettyServerType;
import cn.heimdall.core.utils.spi.Initialize;

/**
 * 处理查询类请求协调器
 */
@LoadLevel(name = LoadLevelConstants.ACTION_COORDINATOR)
public class ActionCoordinator implements MessageDoorway, Coordinator, ActionInboundHandler, Initialize {

    @Override
    public NettyServerType getNettyServerType() {
        return NettyServerType.ACTION;
    }

    @Override
    public AbstractRemotingServer generateServerRemoteInstance() {
        AbstractRemotingServer remotingServer = RemotingInstanceFactory.generateRemotingServer(getNettyServerType());
        //TODO MORE
        remotingServer.doRegisterProcessor(MessageType.TYPE_NODE_REGISTER_REQUEST, new QueryMetricProcessor());
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
        QueryMetricRequest clientMessage = (QueryMetricRequest) request;
        clientMessage.setActionInboundHandler(this);
        //执行inbound的方法
        return clientMessage.handle();
    }

    @Override
    public void onResponse(MessageBody response) {

    }


    @Override
    public QueryMetricResponse handle(QueryMetricRequest request) {
        return null;
    }
}
