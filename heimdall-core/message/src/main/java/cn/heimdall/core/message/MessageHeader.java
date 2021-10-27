package cn.heimdall.core.message;

/**
 * 消息头，通用的消息信息
 */
public class MessageHeader {

    private int version = 1;
    private short typeCode;

    public MessageHeader(){

    }

    public MessageHeader(int version, MessageType messageType){
        this.version = version;
        this.typeCode = messageType.getTypeCode();
    }

    public MessageHeader(MessageType messageType){
        this.typeCode = messageType.getTypeCode();
    }

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

}
