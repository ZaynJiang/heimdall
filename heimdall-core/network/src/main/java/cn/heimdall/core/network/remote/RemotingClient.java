package cn.heimdall.core.network.remote;

import io.netty.channel.Channel;

import java.util.concurrent.TimeoutException;

public interface RemotingClient {
    Object sendSyncRequest(Object msg) throws TimeoutException;
    Object sendSyncRequest(Channel channel, Object msg) throws TimeoutException;
}
