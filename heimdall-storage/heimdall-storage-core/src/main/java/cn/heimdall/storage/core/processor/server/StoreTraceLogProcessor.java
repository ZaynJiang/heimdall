package cn.heimdall.storage.core.processor.server;

import cn.heimdall.core.message.MessageDoorway;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.RemotingServer;

public class StoreTraceLogProcessor extends ServerProcessor {

    public StoreTraceLogProcessor(MessageDoorway messageDoorway, RemotingServer remotingServer) {
        super(messageDoorway, remotingServer);
    }
}
