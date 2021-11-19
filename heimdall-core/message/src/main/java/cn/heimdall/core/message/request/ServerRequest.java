package cn.heimdall.core.message.request;

import cn.heimdall.core.message.InboundHandler;
import cn.heimdall.core.message.MessageBody;

public abstract class ServerRequest extends MessageBody {

    protected InboundHandler handler;

    public void setHandler(InboundHandler handler) {
        this.handler = handler;
    }
}
