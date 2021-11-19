package cn.heimdall.compute.processor.server;

import cn.heimdall.compute.analyzer.AbstractMessageAnalyzer;
import cn.heimdall.compute.analyzer.MessageTreeAnalyzer;
import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.network.processor.ServerProcessor;
import cn.heimdall.core.network.remote.RemotingServer;
import io.netty.channel.ChannelHandlerContext;

/**
 * 客户端发送的消息树
 */
public class MessageTreeProcessor implements ServerProcessor {

    //TODO 初始化
    private AbstractMessageAnalyzer messageAnalyzer;

    private RemotingServer remotingServer;

    public MessageTreeProcessor(RemotingServer remotingServer) {
        messageAnalyzer = new MessageTreeAnalyzer();
        this.remotingServer = remotingServer;
    }

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {
        MessageBody messageBody = message.getMessageBody();
        messageAnalyzer.distribute(messageBody);
        //TODO 处理返回值
    }
}
