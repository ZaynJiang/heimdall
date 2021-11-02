package cn.heimdall.core.network.remote;

import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.message.Message;
import cn.heimdall.core.network.remote.hook.RemoteHook;
import cn.heimdall.core.utils.exception.NetworkException;
import cn.heimdall.core.utils.spi.ServiceLoaderUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class AbstractRemoting {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRemoting.class);

    protected final ServiceLoader<RemoteHook> rpcHooks = ServiceLoaderUtil.getServiceLoader(RemoteHook.class);

    protected final ConcurrentHashMap<Integer, MessageFuture> futures = new ConcurrentHashMap<>();

    protected final Object lock = new Object();


    public void init() {

    }

    public void destroy() {

    }

    protected Object sendSync(Channel channel, Message message, long timeoutMillis) throws TimeoutException {
        if (timeoutMillis <= 0) {
            throw new NetworkException("timeout should more than 0ms");
        }
        if (channel == null) {
            LOGGER.warn("sendSync nothing, caused by null channel.");
            return null;
        }

        MessageFuture messageFuture = new MessageFuture();
        messageFuture.setRequestMessage(message);
        messageFuture.setTimeout(timeoutMillis);
        futures.put(message.getMessageId(), messageFuture);
        channelWritableCheck(channel, message.getMessageBody());
        String remoteAddr = ChannelHelper.getAddressFromChannel(channel);
        doBeforeRpcHooks(remoteAddr, message);
        channel.writeAndFlush(message).addListener((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                MessageFuture messageFuture1 = futures.remove(message.getMessageId());
                if (messageFuture1 != null) {
                    messageFuture1.setResultMessage(future.cause());
                }
                destroyChannel(future.channel());
            }
        });
        try {
            Object result = messageFuture.get(timeoutMillis, TimeUnit.MILLISECONDS);
            doAfterRpcHooks(remoteAddr, message, result);
            return result;
        } catch (Exception exx) {
            LOGGER.error("wait response error:{},ip:{},request:{}", exx.getMessage(), channel.remoteAddress(),
                    message.getMessageBody());
            if (exx instanceof TimeoutException) {
                throw (TimeoutException) exx;
            } else {
                throw new RuntimeException(exx);
            }
        }
    }

    private void channelWritableCheck(Channel channel, Object msg) {
        int tryTimes = 0;
        synchronized (lock) {
            while (!channel.isWritable()) {
                try {
                    tryTimes++;
                    if (tryTimes > NetworkConfig.getMaxNotWriteableRetry()) {
                        destroyChannel(channel);
                        throw new NetworkException("ChannelIsNotWritable, msg:" + ((msg == null) ? "null" : msg.toString()));
                    }
                    lock.wait(NetworkConfig.getNotWriteableCheckMills());
                } catch (InterruptedException exx) {
                    LOGGER.error(exx.getMessage());
                }
            }
        }
    }

    protected void doBeforeRpcHooks(String remoteAddr, Message request) {
        for (RemoteHook remoteHook: rpcHooks) {
            remoteHook.doBeforeRequest(remoteAddr, request);
        }
    }

    protected void doAfterRpcHooks(String remoteAddr, Message request, Object response) {
        for (RemoteHook rpcHook: rpcHooks) {
            rpcHook.doAfterResponse(remoteAddr, request, response);
        }
    }

    public abstract void destroyChannel(Channel channel);

    protected void sendAsync(Channel channel, Message rpcMessage) {
        return;
    }
}
