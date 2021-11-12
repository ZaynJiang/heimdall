package cn.heimdall.compute.processor.client;

import cn.heimdall.core.message.Message;
import cn.heimdall.compute.processor.ClientProcessor;
import io.netty.channel.ChannelHandlerContext;

/**
 * 集群信息变更,比如新增了某个computer之类的
 */
public class ClusterInfoChangeProcessor implements ClientProcessor {
    @Override
    public void process(ChannelHandlerContext ctx, Message message) throws Exception {

    }
}
