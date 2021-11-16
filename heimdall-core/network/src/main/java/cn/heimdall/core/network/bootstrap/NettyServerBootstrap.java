package cn.heimdall.core.network.bootstrap;

import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.network.codec.FrameDecoder;
import cn.heimdall.core.network.codec.FrameEncoder;
import cn.heimdall.core.utils.thread.NamedThreadFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

public class NettyServerBootstrap implements RemotingBootstrap {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerBootstrap.class);
    private final ServerBootstrap serverBootstrap = new ServerBootstrap();
    private final EventLoopGroup eventLoopGroupWorker;
    private final EventLoopGroup eventLoopGroupBoss;
    private final NetworkConfig networkConfig;
    private ChannelHandler[] channelHandlers;
    private int listenPort;
    private volatile ChannelFuture channelFuture;
    private final AtomicBoolean initialized = new AtomicBoolean(false);


    public NettyServerBootstrap(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
        if (networkConfig.enableEpoll()) {
            this.eventLoopGroupBoss = new EpollEventLoopGroup(networkConfig.getBossThreadSize(),
                    new NamedThreadFactory(networkConfig.getBossThreadPrefix(), networkConfig.getBossThreadSize()));
            this.eventLoopGroupWorker = new EpollEventLoopGroup(networkConfig.getServerWorkerThreads(),
                    new NamedThreadFactory(networkConfig.getWorkerThreadPrefix(),
                            networkConfig.getServerWorkerThreads()));
        } else {
            this.eventLoopGroupBoss = new NioEventLoopGroup(networkConfig.getBossThreadSize(),
                    new NamedThreadFactory(networkConfig.getBossThreadPrefix(), networkConfig.getBossThreadSize()));
            this.eventLoopGroupWorker = new NioEventLoopGroup(networkConfig.getServerWorkerThreads(),
                    new NamedThreadFactory(networkConfig.getWorkerThreadPrefix(),
                            networkConfig.getServerWorkerThreads()));
        }
    }

    public void setChannelHandlers(final ChannelHandler... handlers) {
        if (handlers != null) {
            channelHandlers = handlers;
        }
    }

    public void setListenPort(int listenPort) {

        if (listenPort <= 0) {
            throw new IllegalArgumentException("listen port: " + listenPort + " is invalid!");
        }
        this.listenPort = listenPort;
    }

    private void addChannelPipelineLast(Channel channel, ChannelHandler... handlers) {
        if (channel != null && handlers != null) {
            channel.pipeline().addLast(handlers);
        }
    }

    public int getListenPort() {
        return listenPort;
    }

    @Override
    public void start() {
        this.serverBootstrap.group(this.eventLoopGroupBoss, this.eventLoopGroupWorker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, networkConfig.getSoBackLogSize())
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_SNDBUF, networkConfig.getServerSocketSendBufSize())
                .childOption(ChannelOption.SO_RCVBUF, networkConfig.getServerSocketResvBufSize())
                .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK,
                        new WriteBufferWaterMark(networkConfig.getWriteBufferLowWaterMark(),
                                networkConfig.getWriteBufferHighWaterMark()))
                .localAddress(new InetSocketAddress(listenPort))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new IdleStateHandler(networkConfig.getChannelMaxReadIdleSeconds(), 0, 0))
                                .addLast(new FrameDecoder())
                                .addLast(new FrameEncoder());
                        if (channelHandlers != null) {
                            addChannelPipelineLast(ch, channelHandlers);
                        }

                    }
                });

        try {
            channelFuture = this.serverBootstrap.bind(listenPort);
            LOGGER.info("Server started, listen port: {}", listenPort);
        } catch (Exception exx) {
            throw new RuntimeException(exx);
        }
    }

    public void closeFutureSync() {
        try {
            this.channelFuture.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.error("closeFutureSync error, port is {}", this.listenPort);
        }
    }

    @Override
    public void shutdown() {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Shutting server down. ");
            }
            this.eventLoopGroupBoss.shutdownGracefully();
            this.eventLoopGroupWorker.shutdownGracefully();
        } catch (Exception exx) {
            LOGGER.error(exx.getMessage());
        }
    }
}
