package cn.heimdall.core.message.body.register;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.NodeRole;

import java.util.List;

/**
 * 注册节点的角色，一个节点可能有多个角色
 */
public class NodeRegisterRequest extends MessageBody {
    private List<NodeRole> nodeRoles;
    private String ip;
    private int host;

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
        return MessageType.TYPE_NODE_REGISTER;
    }
}
