package cn.heimdall.core.message.body.hearbeat;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.NodeRole;

import java.net.InetSocketAddress;
import java.util.Map;

public class NodeHeartbeatResponse extends MessageBody {

    private String host;
    private String port;

    private Map<NodeRole, Map<InetSocketAddress, Long>> addresses;

    public Map<NodeRole, Map<InetSocketAddress, Long>> getAddresses() {
        return addresses;
    }

    public void setAddresses(Map<NodeRole, Map<InetSocketAddress, Long>> addresses) {
        this.addresses = addresses;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
