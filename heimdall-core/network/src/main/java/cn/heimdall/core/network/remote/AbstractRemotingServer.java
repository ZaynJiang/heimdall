package cn.heimdall.core.network.remote;

import cn.heimdall.core.network.bootstrap.NettyServerBootstrap;
import cn.heimdall.core.network.bootstrap.RemotingBootstrap;
import io.netty.channel.Channel;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeoutException;

public class AbstractRemotingServer extends AbstractRemoting implements RemotingServer{

    private RemotingBootstrap serverBootstrap;


    public AbstractRemotingServer(ThreadPoolExecutor executor) {
        this.serverBootstrap = new NettyServerBootstrap();
    }

    @Override
    public void init() {
        super.init();
        serverBootstrap.start();
    }

    @Override
    public void destroy() {
       //TODO shutdown
    }

    @Override
    public void destroyChannel(Channel channel) {

    }

    @Override
    public Object sendSyncRequest(Channel channel, Object msg) throws TimeoutException {
        return null;
    }

    @Override
    public void sendAsyncRequest(Channel channel, Object msg) {

    }
}
