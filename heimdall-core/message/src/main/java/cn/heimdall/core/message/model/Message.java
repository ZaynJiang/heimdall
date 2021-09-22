package cn.heimdall.core.message.model;

import io.netty.buffer.ByteBuf;

public abstract class Message<T extends MessageBody> {

    private MessageHeader messageHeader;

    private T messageBody;

    public T getMessageBody(){
        return messageBody;
    }

    public void encode(ByteBuf byteBuf) {
        byteBuf.writeInt(messageHeader.getVersion());

        //byteBuf.writeBytes();
    }

    public abstract Class<T> getMessageBodyDecodeClass(int opcode);

    public void decode(ByteBuf msg) {
        int version = msg.readInt();
        long streamId = msg.readLong();
        int opCode = msg.readInt();
    }

}
