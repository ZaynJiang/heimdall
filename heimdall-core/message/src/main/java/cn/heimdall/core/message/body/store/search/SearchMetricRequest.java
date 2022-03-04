package cn.heimdall.core.message.body.store.search;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.ActionMessageRequest;
import cn.heimdall.core.message.body.MessageResponse;
import cn.heimdall.core.message.body.StoreMessageRequest;

public class SearchMetricRequest extends StoreMessageRequest {

    @Override
    public MessageType getMessageType() {
        return MessageType.ACTION_QUERY_METRIC_REQUEST;
    }

    @Override
    public MessageResponse handle() {
        return inboundHandler.handle(this);
    }
}
