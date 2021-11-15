package cn.heimdall.core.network.remote;

import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.NodeRole;
import cn.heimdall.core.network.bootstrap.NettyServerBootstrap;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeoutException;

public class AbstractRemotingServer extends AbstractRemoting implements RemotingServer{

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRemotingServer.class);

    private NettyServerBootstrap serverBootstrap;

    public AbstractRemotingServer(ThreadPoolExecutor executor, NetworkConfig networkConfig) {
        super(executor);
        serverBootstrap = new NettyServerBootstrap(networkConfig);
        //注入一个handle
        serverBootstrap.setChannelHandlers(null);
    }

    @Override
    public void init() {
        super.init();
        serverBootstrap.start();
    }

    @Override
    public void destroy() {
        serverBootstrap.shutdown();
        super.destroy();
    }

    @Override
    public void destroyChannel(Channel channel) {
        channel.disconnect();
        channel.close();
    }

    @Override
    public Object sendSyncRequest(Channel channel, Object msg) throws TimeoutException {
        if (channel == null) {
            throw new RuntimeException("client is not connected");
        }
        return super.sendSync(channel, (Message)msg, NetworkConfig.getRpcRequestTimeout());
    }

    @Override
    public void sendAsyncRequest(Channel channel, Object msg) {
        if (channel == null) {
            throw new RuntimeException("client is not connected");
        }
        super.sendAsync(channel, (Message)msg);
    }
}
