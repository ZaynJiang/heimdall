package cn.heimdall.guarder.processor.server;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.body.register.NodeRegisterRequest;
import cn.heimdall.core.message.body.register.NodeRegisterResponse;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.RemotingServer;
import cn.heimdall.core.utils.common.NetUtil;
import io.netty.channel.ChannelHandlerContext;


public class RegisterRequestProcessor implements ServerProcessor {

    private RemotingServer remotingServer;

    public RegisterRequestProcessor(RemotingServer remotingServer) {
        this.remotingServer = remotingServer;
    }

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {
        NodeRegisterRequest messageBody = (NodeRegisterRequest) message.getMessageBody();
        String ipAndPort = NetUtil.toStringAddress(ctx.channel().remoteAddress());
        //TODO 版本控制, 维护
        NodeRegisterResponse response = new NodeRegisterResponse(true);
        remotingServer.sendAsyncRequest(ctx.channel(), response);
    }

}
