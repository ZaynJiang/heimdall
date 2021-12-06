package cn.heimdall.core.message.body.register;


import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.GuarderMessageRequest;
import cn.heimdall.core.message.body.MessageResponse;

/**
 * 客户端注册请求body
 */
public class ClientRegisterRequest extends GuarderMessageRequest {
    private String appName;
    private String ip;

    public ClientRegisterRequest(String appName, String ip) {
        this.appName = appName;
        this.ip = ip;
    }

    public ClientRegisterRequest(){

    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_CLIENT_REGISTER_REQUEST;
    }

    @Override
    public MessageResponse handle() {
        return inboundHandler.handle(this);
    }

    public String getAppName() {
        return appName;
    }

    public ClientRegisterRequest setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public ClientRegisterRequest setIp(String ip) {
        this.ip = ip;
        return this;
    }

    @Override
    public String toString() {
        return "ClientRegisterRequest{" +
                "appName='" + appName + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
