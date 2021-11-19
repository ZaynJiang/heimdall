package cn.heimdall.compute.processor.server;


import cn.heimdall.compute.analyzer.AbstractMessageAnalyzer;
import cn.heimdall.compute.analyzer.AppStateAnalyzer;
import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.RemotingServer;
import io.netty.channel.ChannelHandlerContext;

/**
 * 客户端发送的应用状态心跳消息
 */
public class AppStateProcessor implements ServerProcessor {

    //TODO 初始化
    private final AbstractMessageAnalyzer messageAnalyzer;

    private final RemotingServer remotingServer;

    public AppStateProcessor(RemotingServer remotingServer) {
        this.messageAnalyzer = new AppStateAnalyzer();
        this.remotingServer = remotingServer;
    }

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {
        MessageBody messageBody = message.getMessageBody();
        messageAnalyzer.distribute(messageBody);
        //todo 处理返回值
    }
}
