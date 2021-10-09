package cn.heimdall.core.message.body;

public class MonitorReportBody implements MessageBody{
    private String reportInfo;

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
