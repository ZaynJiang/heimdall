package cn.heimdall.core.network.remote;

import io.netty.channel.Channel;

import java.util.concurrent.TimeoutException;

/**
 * 服务侧发送消息
 */
public interface RemotingServer {

    Object sendSyncRequest(Channel channel, Object msg) throws TimeoutException;

    void sendAsyncRequest(Channel channel, Object msg);

    void destroy();
}
