package cn.heimdall.core.message.body.heartbeat;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.utils.enums.NodeRole;
import cn.heimdall.core.message.body.MessageResponse;

import java.net.InetSocketAddress;
import java.util.Map;

public class ClientHeartbeatResponse  extends MessageResponse {

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

    @Override
    public MessageType getMessageType() {
        return MessageType.CLIENT_HEARTBEAT_RESPONSE;
    }

    @Override
    public String toString() {
        return "ClientHeartbeatResponse{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", addresses=" + addresses +
                '}' + super.toString();
    }
}
