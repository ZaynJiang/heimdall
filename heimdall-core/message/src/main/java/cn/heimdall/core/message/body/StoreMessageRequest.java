package cn.heimdall.core.message.body;

import cn.heimdall.core.message.hander.StoreInboundHandler;

public abstract class StoreMessageRequest extends AbstractMessageTransaction {
    protected StoreInboundHandler inboundHandler;
    public void setComputeInboundHandler(StoreInboundHandler inboundHandler) {
        this.inboundHandler = inboundHandler;
    }
}
