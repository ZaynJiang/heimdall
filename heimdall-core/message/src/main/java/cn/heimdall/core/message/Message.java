package cn.heimdall.core.message;

import cn.heimdall.core.utils.common.JsonUtil;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public abstract class Message<T extends MessageBody>  {

    private MessageHeader messageHeader;

    private int messageId;

    private int compressorType;

    private T messageBody;

    protected Message() {
    }

    public T getMessageBody(){
        return messageBody;
    }

    public void encode(ByteBuf byteBuf) {
        byteBuf.writeInt(messageHeader.getVersion());
        byteBuf.writeInt(messageHeader.getTypeCode());
        byteBuf.writeBytes(JsonUtil.toJson(messageBody).getBytes());
    }

    public void decode(ByteBuf msg) {
        //读取头信息
        int version = msg.readInt();
        short typeCode = msg.readShort();
        this.messageHeader = wrapHeader(typeCode, version);
        Class<T> bodyClazz = getMessageBodyClass(typeCode);
        //目前使用json协议，需要改成自定义协议
        T body = JsonUtil.fromJson(msg.toString(StandardCharsets.UTF_8), bodyClazz);
        this.messageBody = body;
    }

    public int getCompressorType() {
        return compressorType;
    }

    public Message<T> setCompressorType(int compressorType) {
        this.compressorType = compressorType;
        return this;
    }

    private MessageHeader wrapHeader(short type, int version){
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setTypeCode(type);
        messageHeader.setVersion(version);
        return messageHeader;
    }

    //获取消息体的class
    public abstract Class<T> getMessageBodyClass(int typeCode);

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageBody(T messageBody) {
        this.messageBody = messageBody;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
