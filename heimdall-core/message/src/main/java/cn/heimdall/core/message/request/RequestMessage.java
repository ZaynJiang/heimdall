package cn.heimdall.core.message.request;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageHeader;
import cn.heimdall.core.message.MessageType;

public class RequestMessage extends Message<MessageBody> {

    public RequestMessage(MessageBody messageBody) {
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setTypeCode(messageBody.getMessageType().getTypeCode());
        //TODO 设置头信息
        //messageHeader.setVersion();
        this.setMessageHeader(messageHeader);
        this.setMessageBody(messageBody);
    }

    @Override
    public Class<MessageBody> getMessageBodyClass(int typeCode) {
        return MessageType.fromTypeCode(typeCode).getMessageBodyClass();
    }
}
