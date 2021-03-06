package cn.heimdall.core.message.hander;

import cn.heimdall.core.message.body.origin.AppStateRequest;
import cn.heimdall.core.message.body.origin.AppStateResponse;
import cn.heimdall.core.message.body.origin.MessageTreeRequest;
import cn.heimdall.core.message.body.origin.MessageTreeResponse;

public interface ComputeInboundHandler {
    AppStateResponse handle(AppStateRequest request);
    MessageTreeResponse handle(MessageTreeRequest request);
}
