package cn.heimdall.core.network.remote;

import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.message.Message;
import cn.heimdall.core.network.bootstrap.NettyServerBootstrap;
import cn.heimdall.core.utils.common.NetUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeoutException;

public class AbstractRemotingServer extends AbstractRemoting implements RemotingServer{

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRemotingServer.class);

    private NettyServerBootstrap serverBootstrap;

    public AbstractRemotingServer(ThreadPoolExecutor executor, NetworkConfig networkConfig) {
        super(executor);
        serverBootstrap = new NettyServerBootstrap(networkConfig);
        //注入一个handle
        serverBootstrap.setChannelHandlers(null);
    }

    public void setListenPort(int listenPort) {
        serverBootstrap.setListenPort(listenPort);
    }

    public NettyServerBootstrap getServerBootstrap() {
        return serverBootstrap;
    }

    @Override
    public void init() {
        super.init();
        serverBootstrap.start();
    }

    @Override
    public void destroy() {
        serverBootstrap.shutdown();
        super.destroy();
    }

    @Override
    public void destroyChannel(Channel channel) {
        channel.disconnect();
        channel.close();
    }

    @Override
    public Object sendSyncRequest(Channel channel, Object msg) throws TimeoutException {
        if (channel == null) {
            throw new RuntimeException("client is not connected");
        }
        return super.sendSync(channel, (Message)msg, NetworkConfig.getRpcRequestTimeout());
    }

    @Override
    public void sendAsyncRequest(Channel channel, Object msg) {
        if (channel == null) {
            throw new RuntimeException("client is not connected");
        }
        super.sendAsync(channel, (Message)msg);
    }


    @ChannelHandler.Sharable
    class ServerHandler extends ChannelDuplexHandler {


        @Override
        public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
            if (!(msg instanceof Message)) {
                return;
            }
            processMessage(ctx, (Message) msg);
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
            handleDisconnect(ctx);
            super.channelInactive(ctx);
        }

        private void handleDisconnect(ChannelHandlerContext ctx) {
            final String ipAndPort = NetUtil.toStringAddress(ctx.channel().remoteAddress());

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("channel exx:" + cause.getMessage() + ",channel:" + ctx.channel());
            }

        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
            if (evt instanceof IdleStateEvent) {

            }
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
