package cn.heimdall.core.network.processor.client;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.body.hearbeat.NodeHeartbeatResponse;
import cn.heimdall.core.network.processor.ClientProcessor;
import io.netty.channel.ChannelHandlerContext;

public class NodeHeartbeatProcessor implements ClientProcessor {
    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {
        NodeHeartbeatResponse heartbeatResponse = (NodeHeartbeatResponse) message.getMessageBody();
    }
}
