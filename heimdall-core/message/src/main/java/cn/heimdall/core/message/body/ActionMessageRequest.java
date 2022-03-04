package cn.heimdall.core.message.body;

import cn.heimdall.core.message.hander.ActionInboundHandler;

public abstract class ActionMessageRequest extends AbstractMessageTransaction {
    protected ActionInboundHandler inboundHandler;

    public void setActionInboundHandler(ActionInboundHandler inboundHandler) {
        this.inboundHandler = inboundHandler;
    }
}
