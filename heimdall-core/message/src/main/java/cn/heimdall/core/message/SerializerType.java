package cn.heimdall.core.message;

public enum SerializerType {
    HEIMDALL((byte)0x1),

    PROTOBUF((byte)0x2),

    JSON((byte)0x3);

    private final byte code;

    SerializerType(final byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
