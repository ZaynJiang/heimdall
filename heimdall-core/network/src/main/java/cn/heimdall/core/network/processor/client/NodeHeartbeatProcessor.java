package cn.heimdall.core.network.processor.client;

import cn.heimdall.core.cluster.ClusterInfoManager;
import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.body.heartbeat.NodeHeartbeatResponse;
import cn.heimdall.core.network.processor.ClientProcessor;
import io.netty.channel.ChannelHandlerContext;

/**
 * 客户端发起guarder心跳后，更新自身的值
 */
public class NodeHeartbeatProcessor implements ClientProcessor {
    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {
        NodeHeartbeatResponse heartbeatResponse = (NodeHeartbeatResponse) message.getMessageBody();
        ClusterInfoManager.getInstance().doUpdateNodeInfo(heartbeatResponse);
    }
}
