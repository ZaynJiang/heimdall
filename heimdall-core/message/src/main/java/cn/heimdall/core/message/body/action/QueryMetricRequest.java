package cn.heimdall.core.message.body.action;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.ActionMessageRequest;
import cn.heimdall.core.message.body.MessageResponse;

public class QueryMetricRequest extends ActionMessageRequest {
    @Override
    public MessageResponse handle() {
        return actionInboundHandler.handle(this);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_QUERY_METRIC_REQUEST;
    }
}
