package cn.heimdall.core.message.body.store.search;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.MessageResponse;
import cn.heimdall.core.message.response.ResponseMessage;

public class SearchAppStateResponse extends MessageResponse {
    @Override
    public MessageType getMessageType() {
        return MessageType.STORE_SEARCH_APP_STATE_RESPONSE;
    }
}
