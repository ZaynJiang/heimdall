package cn.heimdall.core.network.codec;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.RpcMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class ProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        Message requestMessage = new RpcMessage();
        requestMessage.decode(byteBuf);
        out.add(requestMessage);
    }
}
