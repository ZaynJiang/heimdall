package cn.heimdall.core.network.processor.client;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.body.register.NodeRegisterResponse;
import cn.heimdall.core.network.processor.ClientProcessor;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class NodeRegisterResponseProcessor implements ClientProcessor {

    private final Logger LOGGER = LogManager.getLogger(getClass());

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {
        NodeRegisterResponse heartbeatResponse = (NodeRegisterResponse) message.getMessageBody();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("NodeRegisterProcessor, node register and response :" + heartbeatResponse);
        }
    }
}
