package cn.heimdall.core.network.remote;

import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.network.bootstrap.NettyClientBootstrap;
import cn.heimdall.core.network.bootstrap.RemotingBootstrap;
import io.netty.channel.Channel;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeoutException;

public abstract class AbstractRemotingClient extends AbstractRemoting implements RemotingClient {

    private RemotingBootstrap clientBootstrap;

    private ThreadPoolExecutor executor;

    private ClientChannelManager clientChannelManager;

    public AbstractRemotingClient(){

    }

    public AbstractRemotingClient(NetworkConfig networkConfig, EventExecutorGroup eventExecutorGroup, ThreadPoolExecutor executor) {
        this.executor = executor;
        //TODO 还有一些其它的初始化工作
        this.clientBootstrap = new NettyClientBootstrap(networkConfig, eventExecutorGroup);
        clientChannelManager = new ClientChannelManager();
    }

    @Override
    public void init() {
        clientBootstrap.start();
    }

    @Override
    public Object sendSyncRequest(Object msg) throws TimeoutException {
        return null;
    }

    @Override
    public Object sendSyncRequest(Channel channel, Object msg) throws TimeoutException {
        return null;
    }
}
