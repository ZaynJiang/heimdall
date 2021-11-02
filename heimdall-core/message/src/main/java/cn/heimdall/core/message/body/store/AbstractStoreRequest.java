package cn.heimdall.core.message.body.store;

/**
 *  发送给存储器的消息
 */
public abstract class AbstractStoreRequest {
    /**
     *  计算处理器ip
     */
    private String computeIp;

    /**
     * 应用名
     */
    private String domain;

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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAddressIp() {
        return addressIp;
    }

    public void setAddressIp(String addressIp) {
        this.addressIp = addressIp;
    }
}
