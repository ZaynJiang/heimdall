package cn.heimdall.core.message.body.action;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.MessageResponse;

public class QueryTraceResponse extends MessageResponse {
    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_QUERY_TRANCE_LOG_RESPONSE;
    }
}
