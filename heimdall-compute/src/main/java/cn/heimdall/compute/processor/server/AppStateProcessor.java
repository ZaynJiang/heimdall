package cn.heimdall.compute.processor.server;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageDoorway;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.RemotingServer;
import io.netty.channel.ChannelHandlerContext;

/**
 * 客户端发送的应用状态心跳消息
 */
public class AppStateProcessor implements ServerProcessor {

    private final MessageDoorway messageDoorway;
    private final RemotingServer remotingServer;

    public AppStateProcessor(RemotingServer remotingServer, MessageDoorway messageDoorway) {
        this.remotingServer = remotingServer;
        this.messageDoorway = messageDoorway;
    }

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {
        MessageBody messageBody = message.getMessageBody();
        MessageBody response = messageDoorway.onRequest(messageBody);
        remotingServer.sendSyncRequest(ctx.channel(), response);
    }
}
