package cn.heimdall.core.message;

public enum ResultCode {
    SUCCESS(0, "成功");
    int code;
    String desc;
    ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
