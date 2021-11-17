package cn.heimdall.storage.processor.server;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.RemotingServer;
import io.netty.channel.ChannelHandlerContext;

public class StoreAppStateProcessor implements ServerProcessor {


    public StoreAppStateProcessor(RemotingServer remotingServer) {

    }

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {

    }
}
