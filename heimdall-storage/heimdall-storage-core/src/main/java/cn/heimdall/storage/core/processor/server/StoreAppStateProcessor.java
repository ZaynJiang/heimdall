package cn.heimdall.storage.core.processor.server;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.RemotingServer;
import io.netty.channel.ChannelHandlerContext;

public class StoreAppStateProcessor implements ServerProcessor {

    private RemotingServer remotingServer;

    public StoreAppStateProcessor(RemotingServer remotingServer) {
        this.remotingServer = remotingServer;
    }

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {

    }
}
