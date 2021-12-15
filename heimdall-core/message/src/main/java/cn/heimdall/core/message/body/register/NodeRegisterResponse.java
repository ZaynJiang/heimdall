package cn.heimdall.core.message.body.register;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.utils.enums.NodeRole;
import cn.heimdall.core.message.body.MessageResponse;

import java.net.InetSocketAddress;
import java.util.Map;

public class NodeRegisterResponse extends MessageResponse {
    private String extraData;
    private boolean identified;
    private String host;
    private String port;

    private Map<NodeRole, Map<InetSocketAddress, Long>> addresses;

    public NodeRegisterResponse(boolean identified) {
        this.identified = identified;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public boolean isIdentified() {
        return identified;
    }

    public void setIdentified(boolean identified) {
        this.identified = identified;
    }

    public String getHost() {
        return host;
    }

    public NodeRegisterResponse setHost(String host) {
        this.host = host;
        return this;
    }

    public String getPort() {
        return port;
    }

    public NodeRegisterResponse setPort(String port) {
        this.port = port;
        return this;
    }

    public Map<NodeRole, Map<InetSocketAddress, Long>> getAddresses() {
        return addresses;
    }

    public NodeRegisterResponse setAddresses(Map<NodeRole, Map<InetSocketAddress, Long>> addresses) {
        this.addresses = addresses;
        return this;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_NODE_REGISTER_RESPONSE;
    }

    @Override
    public String toString() {
        return "NodeRegisterResponse{" +
                "extraData='" + extraData + '\'' +
                ", identified=" + identified +
                ", host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", addresses=" + addresses +
                '}' + super.toString();
    }
}
