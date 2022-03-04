package cn.heimdall.core.message.body.store.search;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.MessageResponse;

public class SearchTraceResponse extends MessageResponse {
    @Override
    public MessageType getMessageType() {
        return MessageType.STORE_SEARCH_TRACE_LOG_RESPONSE;
    }
}
