package cn.heimdall.storage.core;

import cn.heimdall.core.message.body.client.AppStateRequest;
import cn.heimdall.core.message.body.client.AppStateResponse;
import cn.heimdall.core.message.body.client.MessageTreeRequest;
import cn.heimdall.core.message.body.client.MessageTreeResponse;
import cn.heimdall.core.message.hander.ComputeInboundHandler;

public class StorageInboundHandler implements ComputeInboundHandler {
    @Override
    public AppStateResponse handle(AppStateRequest request) {
        return null;
    }

    @Override
    public MessageTreeResponse handle(MessageTreeRequest request) {
        return null;
    }
}
