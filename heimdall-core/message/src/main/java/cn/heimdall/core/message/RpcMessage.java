package cn.heimdall.core.message;

public class RpcMessage extends Message<MessageBody> {

    public RpcMessage() {
    }

    public RpcMessage(MessageBody messageBody) {
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setTypeCode(messageBody.getMessageType().getTypeCode());
        //TODO 设置头信息
        messageHeader.setVersion(1);
        super.setMessageHeader(messageHeader);
        super.setMessageBody(messageBody);
    }

    @Override
    public Class<MessageBody> getMessageBodyClass(short typeCode) {
        return MessageType.fromTypeCode(typeCode).getMessageBodyClass();
    }
}
