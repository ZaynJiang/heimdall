package cn.heimdall.core.message.body;

import cn.heimdall.core.message.MessageBody;

public abstract class MessageResponse extends MessageBody {

    private int resultCode;
    private String msg;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
