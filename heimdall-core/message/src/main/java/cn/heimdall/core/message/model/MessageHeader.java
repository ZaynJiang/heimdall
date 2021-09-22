package cn.heimdall.core.message.model;

public class MessageHeader {

    private int version = 1;
    private short typeCode;
    private String messageId;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public short getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(short typeCode) {
        this.typeCode = typeCode;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
