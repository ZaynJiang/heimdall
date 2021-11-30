package cn.heimdall.core.message.body.register;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.NodeRole;
import cn.heimdall.core.message.body.MessageResponse;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * 客户端注册返回body
 */
public class ClientRegisterResponse extends MessageResponse {

    private Map<NodeRole, Map<InetSocketAddress, Long>> addresses;

    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_CLIENT_REGISTER_RESPONSE;
    }

    public Map<NodeRole, Map<InetSocketAddress, Long>> getAddresses() {
        return addresses;
    }

    public ClientRegisterResponse setAddresses(Map<NodeRole, Map<InetSocketAddress, Long>> addresses) {
        this.addresses = addresses;
        return this;
    }
}
