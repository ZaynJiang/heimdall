package cn.heimdall.core.message.body.heartbeat;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.NodeRole;
import cn.heimdall.core.message.body.GuarderMessageRequest;
import cn.heimdall.core.message.body.MessageResponse;

import java.util.List;

public class ClientHeartbeatRequest extends GuarderMessageRequest {
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


    @Override
    public MessageResponse handle() {
        return inboundHandler.handle(this);
    }

    @Override
    public String toString() {
        return "ClientHeartbeatRequest{" +
                "nodeRoles=" + nodeRoles +
                ", ip='" + ip + '\'' +
                ", appName='" + appName + '\'' +
                ", port=" + port +
                '}';
    }
}
