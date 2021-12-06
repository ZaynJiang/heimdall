package cn.heimdall.core.network.remote;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.NodeRole;

import java.util.List;
import java.util.stream.Collectors;

public class ClientPoolKey {

    private String address;
    private MessageBody message;
    private List<NodeRole> nodeRoles;
    private String nodeRoleKey;

    public ClientPoolKey(List<NodeRole> nodeRoles, String address, MessageBody message) {
        this.nodeRoles = nodeRoles;
        this.address = address;
        this.message = message;
        this.nodeRoleKey = nodeRoles.stream().
                map(nr -> String.valueOf(nr.getValue())).collect(Collectors.joining(":"));
    }

    public List<NodeRole> getNodeRoles() {
        return nodeRoles;
    }

    public ClientPoolKey setNodeRoles(List<NodeRole> nodeRoles) {
        this.nodeRoles = nodeRoles;
        return this;
    }

    public String getAddress() {
        return address;
    }


    public ClientPoolKey setAddress(String address) {
        this.address = address;
        return this;
    }

    public MessageBody getMessage() {
        return message;
    }

    public ClientPoolKey setMessage(MessageBody message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("nodeRole:");
        sb.append(nodeRoleKey);
        sb.append(",");
        sb.append("address:");
        sb.append(address);
        sb.append(",");
        sb.append("msg:< ");
        sb.append(" >");
        return sb.toString();
    }

}
