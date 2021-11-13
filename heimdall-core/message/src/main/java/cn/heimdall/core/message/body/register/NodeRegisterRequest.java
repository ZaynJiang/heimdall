package cn.heimdall.core.message.body.register;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.NodeRole;

import java.util.List;

/**
 * 注册节点的角色，一个节点可能有多个角色
 */
public class NodeRegisterRequest extends MessageBody {
    private List<NodeRole> nodeRole;
    private String ip;
    private String port;

    public List<NodeRole> getNodeRole() {
        return nodeRole;
    }

    public void setNodeRole(List<NodeRole> nodeRole) {
        this.nodeRole = nodeRole;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
