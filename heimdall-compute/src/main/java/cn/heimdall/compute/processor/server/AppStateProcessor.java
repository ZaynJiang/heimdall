package cn.heimdall.compute.processor.server;

import cn.heimdall.core.message.MessageDoorway;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.RemotingServer;

/**
 * 客户端发送的应用状态心跳消息
 */
public class AppStateProcessor extends ServerProcessor {
    public AppStateProcessor(MessageDoorway messageDoorway, RemotingServer remotingServer) {
        super(messageDoorway, remotingServer);
    }
}
