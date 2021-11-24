package cn.heimdall.core.message.body.register;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.MessageResponse;

public class NodeRegisterResponse extends MessageResponse {
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

    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_NODE_REGISTER_RESPONSE;
    }
}
