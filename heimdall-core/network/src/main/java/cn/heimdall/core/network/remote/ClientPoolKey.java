package cn.heimdall.core.network.remote;

import cn.heimdall.core.config.constants.ClientRole;
import cn.heimdall.core.message.Message;

public class ClientPoolKey {

    private ClientRole clientRole;
    private String address;
    private Message message;

    public ClientPoolKey(ClientRole clientRole, String address, Message message) {
        this.clientRole = clientRole;
        this.address = address;
        this.message = message;
    }

    public ClientRole getNettyRole() {
        return clientRole;
    }


    public ClientPoolKey setNettyRole(ClientRole clientRole) {
        this.clientRole = clientRole;
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
        sb.append(clientRole.getValue());
        sb.append(",");
        sb.append("address:");
        sb.append(address);
        sb.append(",");
        sb.append("msg:< ");
        sb.append(" >");
        return sb.toString();
    }

}
