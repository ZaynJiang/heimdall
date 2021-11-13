package cn.heimdall.core.message.body.register;

import cn.heimdall.core.message.MessageBody;

public class NodeRegisterResponse extends MessageBody {
    private String extraData;
    private boolean identified;

    public NodeRegisterResponse(boolean identified) {
        this.identified = identified;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public boolean isIdentified() {
        return identified;
    }

    public void setIdentified(boolean identified) {
        this.identified = identified;
    }
}
