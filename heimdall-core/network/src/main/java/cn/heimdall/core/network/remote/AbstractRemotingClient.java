package cn.heimdall.core.network.remote;

import cn.heimdall.core.network.bootstrap.RemotingBootstrap;
import io.netty.channel.Channel;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeoutException;

public abstract class AbstractRemotingClient extends AbstractRemoting implements RemotingClient {

    private RemotingBootstrap clientBootstrap;

    private ThreadPoolExecutor executor;

    public AbstractRemotingClient(RemotingBootstrap clientBootstrap, ThreadPoolExecutor executor) {
        this.executor = executor;
        this.clientBootstrap = clientBootstrap;
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
