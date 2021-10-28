package cn.heimdall.core.utils.exception;

public enum ErrorCode {
    UnKnown("0000", "undowk", "未知错误"),
    NetConnect("0101", "Can not connect to the server", "连接服务器失败");

    private String errCode;

    private String errorMessage;

    private String errorDispose;

    ErrorCode(String errCode, String errorMessage, String errorDispose) {
        this.errCode = errCode;
        this.errorMessage = errorMessage;
        this.errorDispose = errorDispose;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorDispose() {
        return errorDispose;
    }

    @Override
    public String toString() {
        return String.format("[%s] [%s] [%s]", errCode, errorDispose, errorMessage);
    }
}
