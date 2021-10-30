package cn.heimdall.core.network.remote;

import io.netty.channel.Channel;

import java.util.concurrent.TimeoutException;

public interface RemotingClient {
    Object sendSyncRequest(Object msg) throws TimeoutException;
    Object sendSyncRequest(Channel channel, Object msg) throws TimeoutException;
    //客户端注册成功
    void onRegisterMsgSuccess(String serverAddress, Channel channel);
    //客户端注册失败
    void onRegisterMsgFail(String serverAddress, Channel channel);

}
