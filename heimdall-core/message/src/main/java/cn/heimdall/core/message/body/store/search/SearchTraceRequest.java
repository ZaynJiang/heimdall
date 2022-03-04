package cn.heimdall.core.message.body.store.search;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.ActionMessageRequest;
import cn.heimdall.core.message.body.MessageResponse;
import cn.heimdall.core.message.body.store.AbstractStoreRequest;
import cn.heimdall.core.message.hander.StoreInboundHandler;

public class SearchTraceRequest extends AbstractStoreRequest {
    @Override
    public MessageResponse handle() {
        return inboundHandler.handle(this);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.ACTION_QUERY_TRANCE_LOG_REQUEST;
    }
}
