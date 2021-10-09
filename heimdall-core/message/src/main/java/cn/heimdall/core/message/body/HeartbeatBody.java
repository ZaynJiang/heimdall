package cn.heimdall.core.message.body;

/**
 * 客户端的jvm等指标
 */
public class HeartbeatBody implements MessageBody{
    private String gcJson;
    private String sysJson;
    private String threadJson;

    @Override
    public String getDomain() {
        return null;
    }

    @Override
    public void setDomain(String domain) {

    }

    @Override
    public String getIpAddress() {
        return null;
    }

    @Override
    public void setIpAddress(String ipAddress) {

    }
}
