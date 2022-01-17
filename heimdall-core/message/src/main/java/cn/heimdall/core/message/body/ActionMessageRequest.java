package cn.heimdall.core.message.body;

import cn.heimdall.core.message.hander.ActionInboundHandler;

public abstract class ActionMessageRequest extends AbstractMessageTransaction {
    protected ActionInboundHandler actionInboundHandler;

    public void setActionInboundHandler(ActionInboundHandler actionInboundHandler) {
        this.actionInboundHandler = actionInboundHandler;
    }
}
