package cn.heimdall.compute;

import cn.heimdall.core.config.NetworkConfig;
import cn.heimdall.core.network.remote.ClientChannelManager;
import cn.heimdall.core.network.remote.NettyKeyPoolFactory;
import cn.heimdall.core.network.remote.ClientPoolKey;

import java.util.function.Function;

public class ComputeClientChannelManager extends ClientChannelManager {

    public ComputeClientChannelManager(final NettyKeyPoolFactory nettyKeyPoolFactory, final Function<String, ClientPoolKey> poolKeyFunction,
                                 final NetworkConfig clientConfig) {
        super(nettyKeyPoolFactory, poolKeyFunction, clientConfig);
    }
}
