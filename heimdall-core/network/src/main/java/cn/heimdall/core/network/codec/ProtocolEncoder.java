package cn.heimdall.core.network.codec;

import cn.heimdall.core.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class ProtocolEncoder extends MessageToMessageEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        message.encode(buffer);
        out.add(buffer);
    }
}
