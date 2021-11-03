package cn.heimdall.core.network.bootstrap;

import cn.heimdall.core.config.NetworkConfig;
import io.netty.channel.ChannelHandler;

public class NettyServerBootstrap implements RemotingBootstrap {

    private ChannelHandler[] channelHandlers;

    public NettyServerBootstrap(NetworkConfig networkConfig) {

    }

    public void setChannelHandlers(final ChannelHandler... handlers) {
        if (handlers != null) {
            channelHandlers = handlers;
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {

    }
}
