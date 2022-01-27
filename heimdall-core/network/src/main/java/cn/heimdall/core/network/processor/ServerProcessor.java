package cn.heimdall.core.network.processor;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageDoorway;
import cn.heimdall.core.network.remote.RemotingServer;
import io.netty.channel.ChannelHandlerContext;

public abstract class ServerProcessor implements RemoteProcessor{

    protected final MessageDoorway messageDoorway;
    protected final RemotingServer remotingServer;

    public ServerProcessor(MessageDoorway messageDoorway, RemotingServer remotingServer) {
        this.messageDoorway = messageDoorway;
        this.remotingServer = remotingServer;
    }

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {
        MessageBody messageBody = message.getMessageBody();
        MessageBody response = messageDoorway.onRequest(messageBody);
        remotingServer.sendSyncRequest(ctx.channel(), response);
    }
}
