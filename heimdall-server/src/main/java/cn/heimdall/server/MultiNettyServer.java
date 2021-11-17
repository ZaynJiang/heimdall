package cn.heimdall.server;

import cn.heimdall.core.cluster.NodeInfo;
import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.config.constants.ConfigurationKeys;
import cn.heimdall.server.remote.server.ManageRemotingServer;
import cn.heimdall.server.remote.server.TransportRemotingServer;

public final class MultiNettyServer {

    private final NodeInfo nodeInfo;

    private final Configuration configuration;

    public MultiNettyServer(NodeInfo nodeInfo, Configuration configuration) {
        this.nodeInfo = nodeInfo;
        this.configuration = configuration;
    }

    public void multiNettyServerStart() {
        ManageRemotingServer manageServer = null;
        TransportRemotingServer transportServer = null;
        try {
            //如果是guarder，则需要启动管理类服务
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
            }
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
