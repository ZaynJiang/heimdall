package cn.heimdall.core.network.remote;

import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.NodeRole;
import cn.heimdall.core.message.RpcMessage;
import cn.heimdall.core.message.body.PingMessage;
import cn.heimdall.core.network.bootstrap.NettyClientBootstrap;
import cn.heimdall.core.utils.common.NetUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.plugin2.message.HeartbeatMessage;

import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

public abstract class AbstractRemotingClient extends AbstractRemoting implements RemotingClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRemotingClient.class);

    private NettyClientBootstrap clientBootstrap;

    private final ClientChannelManager clientChannelManager;

    protected final NetworkConfig networkConfig;

    public AbstractRemotingClient(NetworkConfig networkConfig,ThreadPoolExecutor messageExecutor,
                                  EventExecutorGroup eventExecutorGroup) {
        super(messageExecutor);
        //TODO 还有一些其它的初始化工作
        this.clientBootstrap = new NettyClientBootstrap(networkConfig, eventExecutorGroup);
        this.clientBootstrap.setChannelHandlers(new ClientHandler());
        this.networkConfig = networkConfig;
        this.clientChannelManager = new ClientChannelManager(
                new NettyKeyPoolFactory(this, clientBootstrap), getPoolKeyFunction(), networkConfig);
    }

    @Override
    public void init() {
        timerExecutor.scheduleAtFixedRate(() -> {
            //TODO 连接
            clientChannelManager.reconnect(getAvailableAddress());
        }, 10 * 1000L, 10 * 1000L, TimeUnit.MILLISECONDS);
        super.init();
        clientBootstrap.start();
    }

    protected abstract Set<InetSocketAddress> getAvailableAddress();


    protected abstract long getResourceExpireTime();

    protected abstract NodeRole getRemoteRole();

    public abstract String loadBalance();

    @Override
    public Object sendSyncRequest(Object msg) throws TimeoutException {
        Channel channel = clientChannelManager.acquireChannel(loadBalance());
        Message message = (Message) msg;
        int timeoutMillis = NetworkConfig.getRpcRequestTimeout();
        return super.sendSync(channel, message, timeoutMillis);
    }

    @Override
    public Object sendSyncRequest(Channel channel, Object msg) throws TimeoutException { ;
        int timeoutMillis = NetworkConfig.getRpcRequestTimeout();
        return super.sendSync(channel, (Message) msg, timeoutMillis);
    }

    public ClientChannelManager getClientChannelManager() {
        return clientChannelManager;
    }

    @Override
    public void destroyChannel(Channel channel) {
        clientChannelManager.destroyChannel(ChannelHelper.getAddressFromChannel(channel), channel);
    }

    @Override
    public void destroy() {
        clientBootstrap.shutdown();
        super.destroy();
    }

    //获取一个获取PoolKey的函数
    protected abstract Function<String, ClientPoolKey> getPoolKeyFunction();

    protected abstract boolean isRegisterSuccess(MessageBody body);


    /**
     *  客户端的handler
     */
    @ChannelHandler.Sharable
    class ClientHandler extends ChannelDuplexHandler {

        @Override
        public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
            if (!(msg instanceof Message)) {
                return;
            }
            processMessage(ctx, (RpcMessage) msg);
        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) {
            synchronized (lock) {
                if (ctx.channel().isWritable()) {
                    lock.notifyAll();
                }
            }
            ctx.fireChannelWritabilityChanged();
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            if (messageExecutor.isShutdown()) {
                return;
            }
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("client handler, channel inactive: {}", ctx.channel());
            }
            clientChannelManager.releaseChannel(ctx.channel(), NetUtil.toStringAddress(ctx.channel().remoteAddress()));
            super.channelInactive(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
                if (idleStateEvent.state() == IdleState.READER_IDLE) {
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("client handler, channel {} read idle.", ctx.channel());
                    }
                    try {
                        String serverAddress = NetUtil.toStringAddress(ctx.channel().remoteAddress());
                        clientChannelManager.invalidateObject(serverAddress, ctx.channel());
                    } catch (Exception exx) {
                        LOGGER.error("client handler, userEventTriggered error ", exx);
                    } finally {
                        clientChannelManager.releaseChannel(ctx.channel(), ChannelHelper.getAddressFromChannel(ctx.channel()));
                    }
                }
                if (idleStateEvent == IdleStateEvent.WRITER_IDLE_STATE_EVENT) {
                    try {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("client handler, will send ping msg,channel {}", ctx.channel());
                        }
                        //TODO 做一些心跳异步发送
                        sendAsync(ctx.channel(), new RpcMessage(PingMessage.PING));
                    } catch (Throwable throwable) {
                        LOGGER.error("client handler, send request error: {}", throwable.getMessage(), throwable);
                    }
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            LOGGER.error("Exception caught", NetUtil.toStringAddress(ctx.channel().remoteAddress()) + "connect exception. " + cause.getMessage(), cause);
            clientChannelManager.releaseChannel(ctx.channel(), ChannelHelper.getAddressFromChannel(ctx.channel()));
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("remove exception rm channel:{}", ctx.channel());
            }
            super.exceptionCaught(ctx, cause);
        }

        @Override
        public void close(ChannelHandlerContext ctx, ChannelPromise future) throws Exception {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info(ctx + " will closed");
            }
            super.close(ctx, future);
        }
    }

}
