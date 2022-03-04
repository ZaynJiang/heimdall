package cn.heimdall.core.message.body.store;

import cn.heimdall.core.message.body.StoreMessageRequest;

/**
 *  发送给存储器的消息
 */
public abstract class AbstractStoreRequest extends StoreMessageRequest {

    /**
     *  计算处理器ip
     */
    private String computeIp;

    /**
     * 应用名
     */
    private String appName;

    /**
     * 应用ip
     */
    private String addressIp;

    public String getComputeIp() {
        return computeIp;
    }

    public void setComputeIp(String computeIp) {
        this.computeIp = computeIp;
    }

    public String getAddressIp() {
        return addressIp;
    }

    public void setAddressIp(String addressIp) {
        this.addressIp = addressIp;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
