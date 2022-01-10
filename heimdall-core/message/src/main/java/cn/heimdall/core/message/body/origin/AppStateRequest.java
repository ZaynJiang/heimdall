package cn.heimdall.core.message.body.origin;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.MessageResponse;

/**
 * 客户端的心跳等信息指标
 */
public class AppStateRequest extends ClientMessageRequest {
    private String gcJson;
    private String sysJson;
    private String threadJson;

    public String getGcJson() {
        return gcJson;
    }

    public void setGcJson(String gcJson) {
        this.gcJson = gcJson;
    }

    public String getSysJson() {
        return sysJson;
    }

    public void setSysJson(String sysJson) {
        this.sysJson = sysJson;
    }

    public String getThreadJson() {
        return threadJson;
    }

    public void setThreadJson(String threadJson) {
        this.threadJson = threadJson;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_CLIENT_APP_STATE_REQUEST;
    }

    @Override
    public MessageResponse handle() {
        return computeInboundHandler.handle(this);
    }
}
