package cn.heimdall.compute.client;

import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.config.constants.ClientRole;
import cn.heimdall.core.message.body.guarder.RegisterComputeBody;
import cn.heimdall.core.network.remote.AbstractClientChannelManager;
import cn.heimdall.core.network.remote.AbstractRemotingClient;
import cn.heimdall.core.network.remote.ClientPoolKey;
import io.netty.channel.Channel;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;

public class RemotingGuarderClient extends AbstractRemotingClient {
    public RemotingGuarderClient(NetworkConfig networkConfig, AbstractClientChannelManager clientChannelManager, EventExecutorGroup eventExecutorGroup, ThreadPoolExecutor executor) {
        super(networkConfig, clientChannelManager, eventExecutorGroup, executor);
    }

    @Override
    public String loadBalance() {
        return null;
    }

    @Override
    protected Function<String, ClientPoolKey> getPoolKeyFunction() {
        return addressIp -> new ClientPoolKey(ClientRole.STORAGE, addressIp,new RegisterComputeBody());
    }

    @Override
    public void onRegisterMsgSuccess(String serverAddress, Channel channel) {
        getClientChannelManager().registerChannel(serverAddress, channel);
    }

    @Override
    public void onRegisterMsgFail(String serverAddress, Channel channel) {
        //TODO do nothing
    }
}
