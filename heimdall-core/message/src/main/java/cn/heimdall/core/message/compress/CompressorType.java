package cn.heimdall.core.message.compress;

public enum CompressorType {
    NONE((byte) 0),

    LZ4((byte) 5);

    private final byte code;

    CompressorType(final byte code) {
        this.code = code;
    }


    public static CompressorType getByCode(int code) {
        for (CompressorType b : CompressorType.values()) {
            if (code == b.code) {
                return b;
            }
        }
        throw new IllegalArgumentException("unknown codec:" + code);
    }


    public static CompressorType getByName(String name) {
        for (CompressorType b : CompressorType.values()) {
            if (b.name().equalsIgnoreCase(name)) {
                return b;
            }
        }
        throw new IllegalArgumentException("unknown codec:" + name);
    }

    public byte getCode() {
        return code;
    }
}
