package cn.heimdall.compute.processor.server;

import cn.heimdall.core.message.MessageDoorway;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.RemotingServer;

/**
 * 客户端发送的消息树
 */
public class MessageTreeProcessor extends ServerProcessor {
    public MessageTreeProcessor(MessageDoorway messageDoorway, RemotingServer remotingServer) {
        super(messageDoorway, remotingServer);
    }
}
