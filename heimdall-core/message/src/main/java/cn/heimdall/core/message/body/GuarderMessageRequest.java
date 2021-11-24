package cn.heimdall.core.message.body;

import cn.heimdall.core.message.hander.GuarderInboundHandler;

public abstract class GuarderMessageRequest extends AbstractMessageTransaction {
    protected GuarderInboundHandler inboundHandler;

    public void setInboundHandler(GuarderInboundHandler inboundHandler) {
        this.inboundHandler = inboundHandler;
    }
}

