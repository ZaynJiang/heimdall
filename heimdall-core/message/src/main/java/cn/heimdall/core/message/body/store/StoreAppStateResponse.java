package cn.heimdall.core.message.body.store;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageType;

public class StoreAppStateResponse extends MessageBody {
    @Override
    public MessageType getMessageType() {
        return MessageType.STORE_APP_STATE_RESPONSE;
    }
}
