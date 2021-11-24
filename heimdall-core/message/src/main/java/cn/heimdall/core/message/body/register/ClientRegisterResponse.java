package cn.heimdall.core.message.body.register;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.MessageResponse;

/**
 * 客户端注册返回body
 */
public class ClientRegisterResponse extends MessageResponse {
    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_CLIENT_REGISTER_RESPONSE;
    }
}
