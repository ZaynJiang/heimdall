package cn.heimdall.core.message.body;

import cn.heimdall.core.message.MessageBody;

public abstract class ClientMessageBody extends MessageBody {
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
