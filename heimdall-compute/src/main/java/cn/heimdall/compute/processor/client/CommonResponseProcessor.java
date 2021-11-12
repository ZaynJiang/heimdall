package cn.heimdall.core.network.processor.client;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.network.processor.ClientProcessor;
import cn.heimdall.core.network.remote.MessageFuture;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentMap;

public class CommonResponseProcessor implements ClientProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonResponseProcessor.class);

    private ConcurrentMap<Integer, MessageFuture> futures;

    public CommonResponseProcessor(ConcurrentMap<Integer, MessageFuture> futures) {
        this.futures = futures;
    }

    public CommonResponseProcessor(){

    }

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {
        MessageFuture messageFuture = futures.remove(message.getMessageId());
        // client发起的
        if (messageFuture != null) {
            messageFuture.setResultMessage(message.getMessageBody());
        } else {
           //TODO 服务端主动发起的request请求
            LOGGER.info("the client received response msg [{}] from tc server.", message.toString());
        }
    }
}
