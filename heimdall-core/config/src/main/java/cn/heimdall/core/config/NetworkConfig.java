package cn.heimdall.core.config;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NetworkConfig extends HeimdallConfig{

    public static int getRpcRequestTimeout() {
        return 30 * 1000;
    }

    public static int getMaxNotWriteableRetry() {
        return 2000;
    }

    public static String getSocketAddressStartChar() {
        return "/";
    }

    public static long getNotWriteableCheckMills() {
        return 10L;
    }


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

    public int getMaxPoolActive() {
        return 1;
    }

    public int getMinPoolIdle() {
        return 0;
    }

    public long getMaxAcquireConnMills() {
        return 60 * 1000L;
    }

    public boolean isPoolTestBorrow() {
        return true;
    }

    public boolean isPoolTestReturn() {
        return true;
    }

    public boolean isPoolLifo() {
        return true;
    }
}
