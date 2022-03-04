package cn.heimdall.core.message.body.store.search;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.ActionMessageRequest;
import cn.heimdall.core.message.body.MessageResponse;
import cn.heimdall.core.message.body.StoreMessageRequest;

public class SearchAppStateRequest extends StoreMessageRequest {

    @Override
    public MessageResponse handle() {
        return inboundHandler.handle(this);
    }
    @Override
    public MessageType getMessageType() {
        return MessageType.STORE_SEARCH_METRIC_REQUEST;
    }
}
