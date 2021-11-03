package cn.heimdall.core.network.processor;

import cn.heimdall.core.message.Message;
import io.netty.channel.ChannelHandlerContext;

public interface RemoteProcessor {
    void process(ChannelHandlerContext ctx, Message message) throws Exception;
}
