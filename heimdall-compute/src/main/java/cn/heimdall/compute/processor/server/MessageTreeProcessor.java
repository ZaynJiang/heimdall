package cn.heimdall.compute.processor.server;

import cn.heimdall.compute.analyzer.AbstractMessageAnalyzer;
import cn.heimdall.core.message.Message;
import cn.heimdall.core.network.processor.ServerProcessor;
import io.netty.channel.ChannelHandlerContext;

/**
 * 客户端发送的消息树
 */
public class MessageTreeProcessor implements ServerProcessor {

    //TODO 初始化
    private AbstractMessageAnalyzer abstractMessageAnalyzer;

/*    @Override
    public ProcessResult execute(MessageBody messageBody) {
        abstractMessageAnalyzer.distribute(messageBody);
        //TODO 返回需要注意计算节点的状态是否正常
        return new ProcessResult();
    }*/

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {

    }
}
