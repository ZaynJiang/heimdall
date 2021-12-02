package cn.heimdall.core.network.handle;

import cn.heimdall.core.message.RpcMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class ComputeProcessHandler extends SimpleChannelInboundHandler<RpcMessage> {

/*
    private ProcessorManager processorManager;
*/

    private final Logger log = LogManager.getLogger(getClass());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMessage rpcMessage) throws Exception {
      /*  ProcessResult processResult = processorManager.processResult(requestMessage);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHeader(requestMessage.getMessageHeader());
        responseMessage.setMessageBody(processResult);

        if (ctx.channel().isActive() && ctx.channel().isWritable()) {
            ctx.writeAndFlush(responseMessage);
        } else {
            log.error("not writable, message is missing, {}");
        }*/
    }


}
