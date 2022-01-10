package cn.heimdall.core.message.body.store;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.RpcMessage;
import cn.heimdall.core.message.body.MessageResponse;
import cn.heimdall.core.message.body.origin.AppStateRequest;

/**
 * 存储心跳消息
 */
public class StoreAppStateRequest extends AbstractStoreRequest {

    private String gcJson;
    private String sysJson;
    private String threadJson;
    private String appName;
    private String ip;
    private Long timeStamp;

    public String getAppName() {
        return appName;
    }

    public String getGcJson() {
        return gcJson;
    }

    public void setGcJson(String gcJson) {
        this.gcJson = gcJson;
    }

    public String getSysJson() {
        return sysJson;
    }

    public void setSysJson(String sysJson) {
        this.sysJson = sysJson;
    }

    public String getThreadJson() {
        return threadJson;
    }

    public void setThreadJson(String threadJson) {
        this.threadJson = threadJson;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public MessageType getMessageType() {
        return null;
    }

    @Override
    public MessageResponse handle() {
        return null;
    }

    public static RpcMessage getRpcMessage(AppStateRequest appStateRequest) {
        StoreAppStateRequest request = new StoreAppStateRequest();
        request.setAppName(appStateRequest.getDomain());
        request.setIp(appStateRequest.getIp());
        request.setGcJson(appStateRequest.getGcJson());
        request.setSysJson(appStateRequest.getSysJson());
        request.setThreadJson(appStateRequest.getThreadJson());
        return new RpcMessage(request);
    }

}
