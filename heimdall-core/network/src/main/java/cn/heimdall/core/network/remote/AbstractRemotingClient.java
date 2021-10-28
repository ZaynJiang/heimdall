package cn.heimdall.core.network.remote;

import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.MessageHeader;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.ClientMessageBody;
import cn.heimdall.core.message.request.RequestMessage;
import cn.heimdall.core.network.bootstrap.NettyClientBootstrap;
import cn.heimdall.core.network.bootstrap.RemotingBootstrap;
import io.netty.channel.Channel;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

public abstract class AbstractRemotingClient extends AbstractRemoting implements RemotingClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRemotingClient.class);

    private RemotingBootstrap clientBootstrap;

    private ThreadPoolExecutor executor;

    private AbstractClientChannelManager clientChannelManager;

    public AbstractRemotingClient(NetworkConfig networkConfig, AbstractClientChannelManager clientChannelManager,
                                  EventExecutorGroup eventExecutorGroup, ThreadPoolExecutor executor) {
        this.executor = executor;
        //TODO 还有一些其它的初始化工作
        this.clientBootstrap = new NettyClientBootstrap(networkConfig, eventExecutorGroup);
        this.clientChannelManager = clientChannelManager;
    }

    @Override
    public void init() {
        clientBootstrap.start();
    }

    public abstract String loadBalance();

    @Override
    public Object sendSyncRequest(Object msg) throws TimeoutException {
        Channel channel = clientChannelManager.acquireChannel(loadBalance());
        Message message = buildRequestMessage(msg, MessageType.TYPE_NODE_COMPUTE_HEARTBEAT);
        int timeoutMillis = NetworkConfig.getRpcRequestTimeout();
        return super.sendSync(channel, message, timeoutMillis);
    }

    protected Message buildRequestMessage(Object msg, MessageType messageType) {
        Message rpcMessage = new RequestMessage();
        ClientMessageBody clientMessageBody = (ClientMessageBody)msg;
        rpcMessage.setMessageHeader(new MessageHeader(messageType));
        rpcMessage.setMessageBody(clientMessageBody);
        return rpcMessage;
    }

    @Override
    public Object sendSyncRequest(Channel channel, Object msg) throws TimeoutException {
        return null;
    }

    //获取一个获取PoolKey的函数
    protected abstract Function<String, ClientPoolKey> getPoolKeyFunction();

}
