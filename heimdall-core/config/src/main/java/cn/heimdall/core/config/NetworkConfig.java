package cn.heimdall.core.config;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NetworkConfig {

    public int getClientSelectorThreadSize() {
        //TODO 从配置文件获取
        return 1;
    }

    public String getClientSelectorThreadPrefix() {
        //TODO 从配置文件获取
        return "NettyClientSelector";
    }

    public int getClientWorkerThreads() {
        return 1;
    }


    public int getConnectTimeoutMillis() {
        return 10000;
    }

    public String getClientWorkerThreadPrefix() {
        return "NettyClientWorkerThread";
    }

    public Class<? extends Channel> getClientChannelClazz() {
        return  NioSocketChannel.class;
    }

    public int getClientSocketRcvBufSize() {
        return 153600;
    }

    public boolean enableNative() {
        return false;
    }

    public int getClientSocketSndBufSize() {
        return 153600;
    }

    public int getChannelMaxWriteIdleSeconds() {
        return 15;
    }

    public int getChannelMaxReadIdleSeconds() {
        return 15;
    }

    public int getChannelMaxAllIdleSeconds() {
        return 0;
    }

}
