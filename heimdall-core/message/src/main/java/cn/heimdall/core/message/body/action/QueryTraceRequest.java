package cn.heimdall.core.message.body.action;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.ActionMessageRequest;
import cn.heimdall.core.message.body.MessageResponse;

public class QueryTraceRequest extends ActionMessageRequest {
    @Override
    public MessageResponse handle() {
        return null;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_QUERY_TRANCE_LOG_REQUEST;
    }
}
