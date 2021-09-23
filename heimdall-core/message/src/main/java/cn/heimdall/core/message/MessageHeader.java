package cn.heimdall.core.message;

public class MessageHeader {

    private int version = 1;
    private short typeCode;

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
