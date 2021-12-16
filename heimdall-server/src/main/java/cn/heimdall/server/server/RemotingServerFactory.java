package cn.heimdall.server.server;


import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.config.constants.ConfigurationKeys;
import cn.heimdall.core.network.remote.AbstractRemotingServer;
import cn.heimdall.core.utils.enums.NettyServerType;

public class RemotingServerFactory {

    public static AbstractRemotingServer makeRemotingServer(NettyServerType nettyServerType, Configuration configuration) {
        switch (nettyServerType) {
            case ACTION:
                AbstractRemotingServer actionServer = ActionRemotingServer.getInstance();
                actionServer.setListenPort(configuration.getInt(ConfigurationKeys.HTTP_PORT, 7400));
                actionServer.init();
                return actionServer;
            case TRANSPORT:
                AbstractRemotingServer managerServer = TransportRemotingServer.getInstance();
                managerServer.setListenPort(configuration.getInt(ConfigurationKeys.MANAGE_PORT, 7200));
                managerServer.init();
                return managerServer;
            case MANAGE:
                AbstractRemotingServer transportServer = ManageRemotingServer.getInstance();
                transportServer.setListenPort(configuration.getInt(ConfigurationKeys.TRANSPORT_PORT, 7300));
                transportServer.init();
                return transportServer;
        }
        return null;
    }
}
