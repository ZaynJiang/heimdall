package cn.heimdall.core.message.body.heartbeat;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.NodeRole;

import java.util.List;

public class ClientHeartbeatRequest extends MessageBody {
    private List<NodeRole> nodeRoles;
    private String ip;
    private String appName;
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
        return MessageType.TYPE_CLIENT_HEARTBEAT_REQUEST;
    }

    public String getAppName() {
        return appName;
    }

    public ClientHeartbeatRequest setAppName(String appName) {
        this.appName = appName;
        return this;
    }
}
