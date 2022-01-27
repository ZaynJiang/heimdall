package cn.heimdall.core.message.body.origin;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.MessageResponse;

public class MessageTreeResponse extends MessageResponse {
    @Override
    public MessageType getMessageType() {
        return MessageType.MESSAGE_TREE_RESPONSE;
    }
}
