package cn.heimdall.server;

import cn.heimdall.core.network.codec.FrameDecoder;
import cn.heimdall.core.network.codec.FrameEncoder;
import cn.heimdall.core.network.codec.ProtocolDecoder;
import cn.heimdall.core.network.codec.ProtocolEncoder;
import cn.heimdall.core.network.handle.ComputeProcessHandler;
import cn.heimdall.core.network.handle.IdleCheckHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.flush.FlushConsolidationHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutionException;


@Deprecated
public class ServerBootStrap {
    public static void main(String[] args) throws InterruptedException, ExecutionException, CertificateException, SSLException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.channel(NioServerSocketChannel.class)
                .option(NioChannelOption.SO_BACKLOG, 1024)
                .childOption(NioChannelOption.TCP_NODELAY, true)
                .handler(new LoggingHandler(LogLevel.INFO));

        //thread
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(0, new DefaultThreadFactory("boss"));
        NioEventLoopGroup workGroup = new NioEventLoopGroup(0, new DefaultThreadFactory("worker"));

        try {
            serverBootstrap.group(bossGroup, workGroup);
            LoggingHandler debugLogHandler = new LoggingHandler(LogLevel.DEBUG);
            LoggingHandler infoLogHandler = new LoggingHandler(LogLevel.INFO);
            serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("debegLog", debugLogHandler);

                    pipeline.addLast("idleHandler", new IdleCheckHandler());


                    pipeline.addLast("frameDecoder", new FrameDecoder());
                    pipeline.addLast("frameEncoder", new FrameEncoder());

                    pipeline.addLast("protocolDecoder", new ProtocolDecoder());
                    pipeline.addLast("protocolEncoder", new ProtocolEncoder());


                    pipeline.addLast("infolog", infoLogHandler);

                    pipeline.addLast("flushEnhance", new FlushConsolidationHandler(10, true));

                    //新增计算消息处理
                    pipeline.addLast(workGroup, new ComputeProcessHandler());
                }
            });

            ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();

            channelFuture.channel().closeFuture().sync();
        } finally{
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
