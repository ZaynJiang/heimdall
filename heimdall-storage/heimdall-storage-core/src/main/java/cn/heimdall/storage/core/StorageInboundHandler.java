package cn.heimdall.storage.core;

import cn.heimdall.core.message.body.origin.AppStateRequest;
import cn.heimdall.core.message.body.origin.AppStateResponse;
import cn.heimdall.core.message.body.origin.MessageTreeRequest;
import cn.heimdall.core.message.body.origin.MessageTreeResponse;
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
