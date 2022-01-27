package cn.heimdall.core.message.body.heartbeat;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.utils.enums.NodeRole;

import java.util.List;

public class NodeHeartbeatRequest extends MessageBody {
    private List<NodeRole> nodeRoles;
    private String ip;
    private int port;

    public List<NodeRole> getNodeRoles() {
        return nodeRoles;
    }

    public void setNodeRoles(List<NodeRole> nodeRoles) {
        this.nodeRoles = nodeRoles;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.NODE_HEARTBEAT_REQUEST;
    }

    @Override
    public String toString() {
        return "NodeHeartbeatRequest{" +
                "nodeRoles=" + nodeRoles +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
