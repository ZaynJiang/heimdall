package cn.heimdall.guarder.processor.server;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageDoorway;
import cn.heimdall.core.message.body.register.NodeRegisterRequest;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.RemotingServer;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.TimeoutException;


public class RegisterRequestProcessor implements ServerProcessor {

    private final RemotingServer remotingServer;
    private final MessageDoorway messageDoorway;

    public RegisterRequestProcessor(RemotingServer remotingServer, MessageDoorway messageDoorway) {
        this.messageDoorway = messageDoorway;
        this.remotingServer = remotingServer;
    }

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws TimeoutException {
        MessageBody messageBody = message.getMessageBody();
        MessageBody messageResponse = messageDoorway.onRequest(messageBody);
        remotingServer.sendAsyncRequest(ctx.channel(), messageResponse);
    }

}
