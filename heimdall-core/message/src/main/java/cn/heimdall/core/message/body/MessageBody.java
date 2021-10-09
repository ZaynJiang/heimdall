package cn.heimdall.core.message.body;

public interface MessageBody{
    String getDomain();
    void setDomain(String domain);
    String getIpAddress();
    void setIpAddress(String ipAddress);
}
