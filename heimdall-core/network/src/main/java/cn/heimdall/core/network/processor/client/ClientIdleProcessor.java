package cn.heimdall.core.network.processor.client;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.network.processor.ClientProcessor;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientIdleProcessor implements ClientProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientIdleProcessor.class);


    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("ClientIdleProcessor received pong", message);
        }
    }
}
