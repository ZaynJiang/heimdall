package cn.heimdall.core.message;

import cn.heimdall.core.message.body.MessageBody;
import cn.heimdall.core.utils.common.JsonUtils;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public abstract class Message<T extends MessageBody>  {

    private MessageHeader messageHeader;

    private T messageBody;

    public T getMessageBody(){
        return messageBody;
    }

    public void encode(ByteBuf byteBuf) {
        byteBuf.writeInt(messageHeader.getVersion());
        byteBuf.writeInt(messageHeader.getTypeCode());
        byteBuf.writeBytes(JsonUtils.toJson(messageBody).getBytes());
    }

    public void decode(ByteBuf msg) {
        int version = msg.readInt();
        short typeCode = msg.readShort();
        this.messageHeader = wrapHeader(typeCode, version);
        Class<T> bodyClazz = getMessageBodyClass(typeCode);
        //目前使用json协议，需要改成自定义协议
        T body = JsonUtils.fromJson(msg.toString(StandardCharsets.UTF_8), bodyClazz);
        this.messageBody = body;
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
}
