package cn.heimdall.server;

import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.network.coordinator.Coordinator;
import cn.heimdall.core.network.remote.AbstractRemotingServer;
import cn.heimdall.core.utils.common.CollectionUtil;
import cn.heimdall.server.server.ActionRemotingServer;
import cn.heimdall.server.server.ManageRemotingServer;
import cn.heimdall.server.server.RemotingServerFactory;
import cn.heimdall.server.server.TransportRemotingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class NettyServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    private final Set<Coordinator> coordinators;

    private final Configuration configuration;

    public NettyServer(Set<Coordinator> coordinators, Configuration configuration) {
        this.coordinators = coordinators;
        this.configuration = configuration;
    }


    public void multiNettyServerStart() {
        List<AbstractRemotingServer> servers = null;
        try {
            servers = coordinators.stream().
                    map(coordinator -> {
                        AbstractRemotingServer remotingServer = RemotingServerFactory.makeRemotingServer(coordinator.getNettyServerType(), configuration);
                        //进行注册处理器操作
                        return remotingServer;
                    })
                    .collect(Collectors.toList());
        } catch (Throwable e) {
            LOGGER.error("multiNettyServerStart error, ", e);
        } finally {
            if (!CollectionUtil.isEmpty(servers)) {
                servers.stream().forEach(server -> server.getServerBootstrap().closeFutureSync());
            }
        }


    }
}
