package cn.heimdall.guarder.processor.server;

import cn.heimdall.core.cluster.ClusterInfoManager;
import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.body.heartbeat.NodeHeartbeatRequest;
import cn.heimdall.core.message.body.register.NodeRegisterResponse;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.RemotingServer;
import io.netty.channel.ChannelHandlerContext;

public class HeartbeatRequestProcessor implements ServerProcessor {

    private RemotingServer remotingServer;

    public HeartbeatRequestProcessor(RemotingServer remotingServer) {
        this.remotingServer = remotingServer;
    }

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {
        NodeHeartbeatRequest messageBody = (NodeHeartbeatRequest) message.getMessageBody();
        //集群信息注册
        ClusterInfoManager.getInstance().doUpdateNodeInfo(messageBody);

        //TODO 设置返回值
        NodeRegisterResponse response = new NodeRegisterResponse(true);
        remotingServer.sendAsyncRequest(ctx.channel(), response);
    }
}
