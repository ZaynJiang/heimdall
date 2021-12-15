package cn.heimdall.server;

import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.network.coordinator.Coordinator;
import cn.heimdall.server.server.ManageRemotingServer;
import cn.heimdall.server.server.TransportRemotingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public final class NettyServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    private final Set<Coordinator> coordinators;

    private final Configuration configuration;

    public NettyServer(Set<Coordinator> coordinators, Configuration configuration) {
        this.coordinators = coordinators;
        this.configuration = configuration;
    }



    public void multiNettyServerStart() {
        ManageRemotingServer manageServer = null;
        TransportRemotingServer transportServer = null;
        try {
            coordinators.stream().forEach(coordinator -> {

            });
      /*      //如果是guarder，则需要启动管理类服务
            if (nodeInfo.isGuarder()) {
                //启动管理类server
                manageServer = ManageRemotingServer.getInstance();
                manageServer.setListenPort(configuration.getInt(ConfigurationKeys.MANAGE_PORT, 7200));
                manageServer.init();
            }
            //如果是存储器和计算者,则需要启动业务数据处理服务
            if (nodeInfo.isStorage() || nodeInfo.isCompute()) {
                transportServer = TransportRemotingServer.getInstance();
                transportServer.setListenPort(configuration.getInt(ConfigurationKeys.TRANSPORT_PORT, 7300));
                transportServer.init();
            }*/
        } catch (Throwable e){
            LOGGER.error("multiNettyServerStart error, ", e);
        } finally {
            if (manageServer != null) {
                manageServer.getServerBootstrap().closeFutureSync();
            }
            if (transportServer != null) {
                transportServer.getServerBootstrap().closeFutureSync();
            }
        }


    }
}
