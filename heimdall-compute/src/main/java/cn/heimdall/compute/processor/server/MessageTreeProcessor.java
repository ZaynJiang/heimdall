package cn.heimdall.compute.processor.server;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageDoorway;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.RemotingServer;
import io.netty.channel.ChannelHandlerContext;

/**
 * 客户端发送的消息树
 */
public class MessageTreeProcessor implements ServerProcessor {

    private final MessageDoorway messageDoorway;
    private final RemotingServer remotingServer;

    public MessageTreeProcessor(RemotingServer remotingServer, MessageDoorway messageDoorway) {
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
