package cn.heimdall.guarder.processor.server;

import cn.heimdall.core.cluster.ClusterInfoManager;
import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.body.register.NodeRegisterRequest;
import cn.heimdall.core.message.body.register.NodeRegisterResponse;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.RemotingServer;
import io.netty.channel.ChannelHandlerContext;


public class RegisterRequestProcessor implements ServerProcessor {

    private RemotingServer remotingServer;

    public RegisterRequestProcessor(RemotingServer remotingServer) {
        this.remotingServer = remotingServer;
    }

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {
        NodeRegisterRequest messageBody = (NodeRegisterRequest) message.getMessageBody();
        //集群信息注册
        ClusterInfoManager.getInstance().doRegisterNodeInfo(messageBody);

        //TODO 设置返回值
        NodeRegisterResponse response = new NodeRegisterResponse(true);
        remotingServer.sendAsyncRequest(ctx.channel(), response);
    }

}
