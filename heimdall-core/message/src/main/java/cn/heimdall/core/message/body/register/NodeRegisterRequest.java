package cn.heimdall.core.message.body.register;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.utils.enums.NodeRole;
import cn.heimdall.core.message.body.GuarderMessageRequest;
import cn.heimdall.core.message.body.MessageResponse;

import java.util.List;

/**
 * 注册节点的角色，一个节点可能有多个角色
 */
public class NodeRegisterRequest extends GuarderMessageRequest {
    private List<NodeRole> nodeRoles;
    private String ip;
    private int host;

    public NodeRegisterRequest(List<NodeRole> nodeRoles, String ip) {
        this.nodeRoles = nodeRoles;
        this.ip = ip;
    }

    public List<NodeRole> getNodeRoles() {
        return nodeRoles;
    }

    public void setNodeRoles(List<NodeRole> nodeRoles) {
        this.nodeRoles = nodeRoles;
    }

    public String getIp() {
        return ip;
    }

    public int getHost() {
        return host;
    }

    public void setHost(int host) {
        this.host = host;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_NODE_REGISTER_REQUEST;
    }

    @Override
    public MessageResponse handle() {
        return inboundHandler.handle(this);
    }

    @Override
    public String toString() {
        return "NodeRegisterRequest{" +
                "nodeRoles=" + nodeRoles +
                ", ip='" + ip + '\'' +
                ", host=" + host +
                '}';
    }
}
