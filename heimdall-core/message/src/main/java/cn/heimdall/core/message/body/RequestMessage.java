package cn.heimdall.core.message.body;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageType;

@Deprecated
public class RequestMessage extends Message<MessageBody> {

    @Override
    public Class<MessageBody> getMessageBodyClass(int typeCode) {
        return MessageType.fromTypeCode(typeCode).getRequestBody();
    }
}
