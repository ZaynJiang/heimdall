package cn.heimdall.compute.client;

import cn.heimdall.core.network.remote.AbstractRemotingClient;

public class GuarderRemotingClient extends AbstractRemotingClient {
    @Override
    public String loadBalance() {
        return null;
    }
}
