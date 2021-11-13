package cn.heimdall.core.network.remote;

import cn.heimdall.core.message.NodeRole;
import cn.heimdall.core.message.Message;

public class ClientPoolKey {

    private NodeRole nodeRole;
    private String address;
    private Message message;

    public ClientPoolKey(NodeRole nodeRole, String address, Message message) {
        this.nodeRole = nodeRole;
        this.address = address;
        this.message = message;
    }

    public NodeRole getNettyRole() {
        return nodeRole;
    }


    public ClientPoolKey setNettyRole(NodeRole nodeRole) {
        this.nodeRole = nodeRole;
        return this;
    }


    public String getAddress() {
        return address;
    }


    public ClientPoolKey setAddress(String address) {
        this.address = address;
        return this;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("transactionRole:");
        sb.append(nodeRole.getValue());
        sb.append(",");
        sb.append("address:");
        sb.append(address);
        sb.append(",");
        sb.append("msg:< ");
        sb.append(" >");
        return sb.toString();
    }

}
