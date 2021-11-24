package cn.heimdall.core.message.body.register;


import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.GuarderMessageRequest;
import cn.heimdall.core.message.body.MessageResponse;

/**
 * 客户端注册请求body
 */
public class ClientRegisterRequest extends GuarderMessageRequest {
    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_CLIENT_REGISTER_REQUEST;
    }

    @Override
    public MessageResponse handle() {
        return inboundHandler.handle(this);
    }
}
