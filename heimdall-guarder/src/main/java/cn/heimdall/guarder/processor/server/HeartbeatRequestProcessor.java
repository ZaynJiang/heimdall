package cn.heimdall.guarder.processor.server;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageDoorway;
import cn.heimdall.core.message.body.register.NodeRegisterRequest;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.RemotingServer;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.TimeoutException;

public class HeartbeatRequestProcessor implements ServerProcessor {

    private final RemotingServer remotingServer;
    private final MessageDoorway messageDoorway;

    public HeartbeatRequestProcessor(RemotingServer remotingServer, MessageDoorway messageDoorway) {
        this.remotingServer = remotingServer;
        this.messageDoorway = messageDoorway;
    }

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws TimeoutException {
        NodeRegisterRequest messageBody = (NodeRegisterRequest) message.getMessageBody();
        MessageBody messageResponse = messageDoorway.onRequest(messageBody);
        remotingServer.sendSyncRequest(ctx.channel(), messageResponse);
    }
}
