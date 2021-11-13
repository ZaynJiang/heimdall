package cn.heimdall.guarder.processor.server;

import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.config.ConfigurationFactory;
import cn.heimdall.core.message.Message;
import cn.heimdall.core.network.processor.ServerProcessor;
import io.netty.channel.ChannelHandlerContext;

public class HeartbeatRequestProcessor implements ServerProcessor {

    protected static final Configuration CONFIG = ConfigurationFactory.getInstance();

    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {
        //
    }
}
