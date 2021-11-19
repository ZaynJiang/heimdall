package cn.heimdall.core.message.body;

import cn.heimdall.core.message.MessageType;

/**
 * 客户端的心跳等信息指标
 */
public class HeartbeatBody extends ClientMessageBody {
    private String gcJson;
    private String sysJson;
    private String threadJson;

    @Override
    public MessageType getMessageType() {
        return null;
    }
}
