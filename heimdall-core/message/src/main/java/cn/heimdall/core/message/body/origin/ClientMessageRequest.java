package cn.heimdall.core.message.body.origin;

import cn.heimdall.core.message.body.ComputeMessageRequest;

public abstract class ClientMessageRequest extends ComputeMessageRequest {
    private String domain;
    private String ipAddress;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
