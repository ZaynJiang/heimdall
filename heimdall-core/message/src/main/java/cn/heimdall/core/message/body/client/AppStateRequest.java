package cn.heimdall.core.message.body.client;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.ServerResponse;

/**
 * 客户端的心跳等信息指标
 */
public class AppStateRequest extends ClientMessageRequest {
    private String gcJson;
    private String sysJson;
    private String threadJson;

    @Override
    public MessageType getMessageType() {
        return null;
    }

    @Override
    public ServerResponse handle() {
        return computeInboundHandler.handle(this);
    }
}
