package cn.heimdall.core.network.remote;

import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.MessageTypeAware;
import cn.heimdall.core.network.processor.RemoteProcessor;
import cn.heimdall.core.utils.exception.NetworkException;
import cn.heimdall.core.utils.spi.ServiceLoaderUtil;
import cn.heimdall.core.utils.thread.IncrAtomicCounter;
import cn.heimdall.core.utils.thread.NamedThreadFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class AbstractRemoting {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRemoting.class);

    protected final ServiceLoader<RemoteHook> rpcHooks = ServiceLoaderUtil.getServiceLoader(RemoteHook.class);

    protected final ConcurrentHashMap<Integer, MessageFuture> futures = new ConcurrentHashMap<>();

    protected final ScheduledExecutorService timerExecutor = new ScheduledThreadPoolExecutor(1,
            new NamedThreadFactory("timeoutChecker", 1, true));


    protected final HashMap<Short/*MessageType*/, Pair<RemoteProcessor, ExecutorService>>
            processorTable = new HashMap<>(32);

    protected final IncrAtomicCounter idGenerator = new IncrAtomicCounter();

    protected final ThreadPoolExecutor messageExecutor;

    protected final Object lock = new Object();

    protected volatile long nowMills = 0;

    public AbstractRemoting(ThreadPoolExecutor messageExecutor) {
        this.messageExecutor = messageExecutor;
    }

    /**
     * 注册处理器
     * @param messageType
     * @param processor
     */
    protected void registerProcessor(MessageType messageType, RemoteProcessor processor) {
        Pair<RemoteProcessor, ExecutorService> pair = new Pair<>(processor, messageExecutor);
        this.processorTable.put(messageType.getTypeCode(), pair);
    }

    protected void registerProcessor(MessageType messageType, RemoteProcessor processor, ThreadPoolExecutor messageExecutor) {
        Pair<RemoteProcessor, ExecutorService> pair = new Pair<>(processor, messageExecutor);
        this.processorTable.put(messageType.getTypeCode(), pair);
    }

    protected void processMessage(ChannelHandlerContext ctx, Message message) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("%s messageId:%s, body:%s", this, message.getMessageId(), message.getMessageBody()));
        }
        Object body = message.getMessageBody();
        if (body instanceof MessageTypeAware) {
            MessageTypeAware messageTypeAware = (MessageTypeAware) body;
            MessageType messageType = messageTypeAware.getMessageType();
            final Pair<RemoteProcessor, ExecutorService> pair = this.processorTable.get(messageType.getTypeCode());
            if (pair != null) {
                if (pair.getValue() != null) {
                    try {
                        //使用自己的线程池来处理
                        pair.getValue().execute(() -> {
                            try {
                                pair.getKey().process(ctx, message);
                            } catch (Throwable e) {
                                LOGGER.error("processMessage error, message type {}", messageType.getTypeCode(), e);
                            } finally {
                               //TODO do nothing
                            }
                        });
                    } catch (RejectedExecutionException e) {
                        LOGGER.error("thread pool is full, current max pool size is " + messageExecutor.getActiveCount());
                        String name = ManagementFactory.getRuntimeMXBean().getName();
                        String pid = name.split("@")[0];
                        int idx = new Random().nextInt(100);
                        try {
                            Runtime.getRuntime().exec("jstack " + pid + " >d:/" + idx + ".log");
                        } catch (IOException exx) {
                            LOGGER.error(exx.getMessage());
                        }
                    }
                } else {
                    try {
                        pair.getKey().process(ctx, message);
                    } catch (Throwable th) {
                        LOGGER.error("process error, message info is {}", th.getMessage(), th);
                    }
                }
            } else {
                LOGGER.error("This message type [{}] has no processor.", messageType.getTypeCode());
            }
        } else {
            LOGGER.error("This rpcMessage body[{}] is not MessageTypeAware type.", message);
        }
    }

    public void init() {
        timerExecutor.scheduleAtFixedRate(() -> {
                for (Map.Entry<Integer, MessageFuture> entry : futures.entrySet()) {
                    if (entry.getValue().isTimeout()) {
                        futures.remove(entry.getKey());
                        entry.getValue().setResultMessage(null);
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("timeout clear future: {}", entry.getValue().getRequestMessage().getMessageBody());
                        }
                    }
                }
                nowMills = System.currentTimeMillis();
            }, 3000, 3000, TimeUnit.MILLISECONDS);
    }

    public void destroy() {
        timerExecutor.shutdown();
        messageExecutor.shutdown();
    }

    protected Object sendSync(Channel channel, Message message, long timeoutMillis) throws TimeoutException {
        if (timeoutMillis <= 0) {
            throw new NetworkException("timeout should more than 0ms");
        }
        if (channel == null) {
            LOGGER.warn("sendSync nothing, caused by null channel.");
            return null;
        }
        message.setMessageId(idGenerator.get());
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

    protected void sendAsync(Channel channel, Message message) {
        channelWritableCheck(channel, message.getMessageBody());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("write message:" + message.getMessageBody() + ", channel:" + channel + ",active?"
                    + channel.isActive() + ",writable?" + channel.isWritable() + ",isopen?" + channel.isOpen());
        }

        doBeforeRpcHooks(ChannelHelper.getAddressFromChannel(channel), message);

        channel.writeAndFlush(message).addListener((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                destroyChannel(future.channel());
            }
        });
    }

    public ConcurrentHashMap<Integer, MessageFuture> getFutures() {
        return futures;
    }
}
