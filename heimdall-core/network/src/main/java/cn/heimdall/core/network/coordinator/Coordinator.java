package cn.heimdall.core.network.coordinator;

import cn.heimdall.core.network.remote.AbstractRemotingServer;
import cn.heimdall.core.utils.enums.NettyServerType;

public interface Coordinator {
    NettyServerType getNettyServerType();
    void doRegisterProcessor(AbstractRemotingServer remotingServer);
}
