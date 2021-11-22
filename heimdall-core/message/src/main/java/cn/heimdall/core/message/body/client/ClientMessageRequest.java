package cn.heimdall.core.message.body.client;

import cn.heimdall.core.message.body.ServerRequest;
import cn.heimdall.core.message.hander.ComputeInboundHandler;

public abstract class ClientMessageRequest extends ServerRequest {
    private String domain;
    private String ipAddress;
    protected ComputeInboundHandler computeInboundHandler;

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

    public void setInboundHandler(ComputeInboundHandler computeInboundHandler) {
        this.computeInboundHandler = computeInboundHandler;
    }
}
