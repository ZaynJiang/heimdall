package cn.heimdall.core.network.remote;

import cn.heimdall.core.network.bootstrap.DataServerBootstrap;
import cn.heimdall.core.network.bootstrap.ManageServerBootstrap;
import cn.heimdall.core.network.bootstrap.RemotingBootstrap;
import io.netty.channel.Channel;

import java.util.concurrent.TimeoutException;

public class AbstractRemotingServer extends AbstractRemoting implements RemotingServer{

    private final RemotingBootstrap managerServerBootstrap;
    private final RemotingBootstrap dataServerBootstrap;

    public AbstractRemotingServer() {
        this.managerServerBootstrap = new ManageServerBootstrap();
        this.dataServerBootstrap = new DataServerBootstrap();
        //TODO 可以初始化一些handler
    }

    @Override
    public void init() {
        super.init();
        managerServerBootstrap.start();
    }

    @Override
    public void destroy() {
       //TODO shutdown
    }

    @Override
    public Object sendSyncRequest(Channel channel, Object msg) throws TimeoutException {
        return null;
    }

    @Override
    public void sendAsyncRequest(Channel channel, Object msg) {

    }
}
