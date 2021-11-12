package cn.heimdall.compute.processor.client;

import cn.heimdall.core.message.Message;
import cn.heimdall.compute.processor.ClientProcessor;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHeartbeatProcessor implements ClientProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHeartbeatProcessor.class);

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("received PONG from {}", ctx.channel().remoteAddress());
        }
    }
}
