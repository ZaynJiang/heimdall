package cn.heimdall.core.message.body;

import cn.heimdall.core.message.hander.ComputeInboundHandler;

public abstract class ComputeMessageRequest extends AbstractMessageTransaction {
    protected ComputeInboundHandler computeInboundHandler;
    public void setComputeInboundHandler(ComputeInboundHandler computeInboundHandler) {
        this.computeInboundHandler = computeInboundHandler;
    }
}
