package cn.heimdall.core.message.hander;

import cn.heimdall.core.message.body.client.AppStateRequest;
import cn.heimdall.core.message.body.client.AppStateResponse;
import cn.heimdall.core.message.body.client.MessageTreeRequest;
import cn.heimdall.core.message.body.client.MessageTreeResponse;

/**
 * 处理业务相关的输入请求
 */
public interface ComputeInboundHandler {
    AppStateResponse handle(AppStateRequest request);
    MessageTreeResponse handle(MessageTreeRequest request);
}
