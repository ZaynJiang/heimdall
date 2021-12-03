package cn.heimdall.core.message.body.origin;

import cn.heimdall.core.message.body.ComputeMessageRequest;

public abstract class ClientMessageRequest extends ComputeMessageRequest {
    private String domain;
    private String ip;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIp() {
        return ip;
    }

    public ClientMessageRequest setIp(String ip) {
        this.ip = ip;
        return this;
    }
}
