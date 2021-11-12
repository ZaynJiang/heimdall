package cn.heimdall.compute.processor.server;


import cn.heimdall.compute.analyzer.AbstractMessageAnalyzer;
import cn.heimdall.core.message.Message;
import cn.heimdall.core.network.processor.ServerProcessor;
import io.netty.channel.ChannelHandlerContext;

/**
 * 客户端发送的应用状态心跳消息
 */
public class AppStateProcessor implements ServerProcessor {

    //TODO 初始化
    private AbstractMessageAnalyzer messageAnalyzer;

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {

    }
}
