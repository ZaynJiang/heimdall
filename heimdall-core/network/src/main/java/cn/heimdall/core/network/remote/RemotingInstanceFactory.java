package cn.heimdall.core.network.remote;


import cn.heimdall.core.network.server.ActionRemotingServer;
import cn.heimdall.core.network.server.ManageRemotingServer;
import cn.heimdall.core.network.server.TransportRemotingServer;
import cn.heimdall.core.utils.enums.NettyServerType;

public class RemotingInstanceFactory {

    public static AbstractRemotingServer generateRemotingServer(NettyServerType nettyServerType) {
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
