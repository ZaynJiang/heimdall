package cn.heimdall.action.processor;

import cn.heimdall.core.message.MessageDoorway;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.RemotingServer;

public class QueryProcessor extends ServerProcessor {
    public QueryProcessor(MessageDoorway messageDoorway, RemotingServer remotingServer) {
        super(messageDoorway, remotingServer);
    }
}
