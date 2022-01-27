package cn.heimdall.core.message.body.action;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.MessageResponse;

public class QueryAppStateResponse extends MessageResponse {
    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_QUERY_APP_STATE_RESPONSE;
    }
}
