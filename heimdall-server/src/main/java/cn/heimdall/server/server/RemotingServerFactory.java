package cn.heimdall.server.server;


import cn.heimdall.core.network.remote.AbstractRemotingServer;
import cn.heimdall.core.utils.enums.NettyServerType;

public class RemotingServerFactory {

    public static AbstractRemotingServer makeRemotingServer(NettyServerType nettyServerType) {
        switch (nettyServerType) {
            case ACTION:
                AbstractRemotingServer actionServer = ActionRemotingServer.getInstance();
                return actionServer;
            case TRANSPORT:
                AbstractRemotingServer transportServer = TransportRemotingServer.getInstance();
                return transportServer;
            case MANAGE:
                AbstractRemotingServer manageServer = ManageRemotingServer.getInstance();
                return manageServer;
        }
        return null;
    }
}
