package cn.heimdall.core.message.response;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.MessageType;

public class ResponseMessage extends Message {
    @Override
    public Class getMessageBodyClass(int typeCode) {
        return MessageType.fromTypeCode(typeCode).getMessageBodyClass();
    }
}
