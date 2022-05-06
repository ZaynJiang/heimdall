package client.processor;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.network.processor.ClientProcessor;
import cn.heimdall.core.network.remote.MessageFuture;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentMap;

public class RegisterResponseProcessor implements ClientProcessor {

    private ConcurrentMap<Integer, MessageFuture> futures;

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterResponseProcessor.class);

    public RegisterResponseProcessor(ConcurrentMap<Integer, MessageFuture> futures) {
        this.futures = futures;
    }

    @Override
    public void process(ChannelHandlerContext ctx, Message message) {
        MessageFuture messageFuture = futures.remove(message.getMessageId());
        if (messageFuture != null) {
            messageFuture.setResultMessage(message.getMessageBody());
        } else {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("2RegisterResponseProcessor messageFuture is null received response msg [{}] from guarder server.", message.toString());
            }
        }
    }
}
