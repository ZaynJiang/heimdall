package cn.heimdall.core.network.remote;

import cn.heimdall.core.message.Message;
import io.netty.channel.Channel;

import java.util.concurrent.TimeoutException;

public abstract class AbstractRemoting {

    public void init() {

    }

    public void destroy() {

    }

    protected Object sendSync(Channel channel, Message message, long timeoutMillis) throws TimeoutException {
        return null;
    }

    protected void sendAsync(Channel channel, Message rpcMessage) {
        return;
    }
}
