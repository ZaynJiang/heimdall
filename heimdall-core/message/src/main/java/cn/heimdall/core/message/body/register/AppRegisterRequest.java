package cn.heimdall.core.message.body.register;


import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.GuarderMessageRequest;
import cn.heimdall.core.message.body.MessageResponse;

/**
 * 客户端注册请求body
 */
public class AppRegisterRequest extends GuarderMessageRequest {
    private String appName;
    private String ip;

    public AppRegisterRequest(String appName, String ip) {
        this.appName = appName;
        this.ip = ip;
    }

    public AppRegisterRequest(){

    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CLIENT_REGISTER_REQUEST;
    }

    @Override
    public MessageResponse handle() {
        return inboundHandler.handle(this);
    }

    public String getAppName() {
        return appName;
    }

    public AppRegisterRequest setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public AppRegisterRequest setIp(String ip) {
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
