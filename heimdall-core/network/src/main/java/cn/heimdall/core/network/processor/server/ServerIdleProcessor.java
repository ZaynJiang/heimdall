package cn.heimdall.core.network.processor.server;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.MessageDoorway;
import cn.heimdall.core.message.body.PingMessage;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.RemotingServer;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerIdleProcessor extends ServerProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerIdleProcessor.class);

    public ServerIdleProcessor(MessageDoorway messageDoorway, RemotingServer remotingServer) {
        super(messageDoorway, remotingServer);
    }

    @Override
    public void process(ChannelHandlerContext ctx, Message message)  {
        try {
            remotingServer.sendAsyncRequest(ctx.channel(), PingMessage.PONG);
        } catch (Throwable throwable) {
            LOGGER.error("send response error: {}", throwable.getMessage(), throwable);
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("received PING from {}", ctx.channel().remoteAddress());
        }
    }
}
