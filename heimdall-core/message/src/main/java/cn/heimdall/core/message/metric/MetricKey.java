package cn.heimdall.core.message.metric;

import cn.heimdall.core.utils.enums.MetricType;

public abstract class MetricKey {
    protected String domain;
    protected String ip;
    protected String type;
    protected String name;

    public MetricKey(String domain, String ip, String type, String name) {
        this.domain = domain;
        this.ip = ip;
        this.type = type;
        this.name = name;
    }

    public abstract MetricType getMetricType();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    public abstract String getMetricKey();


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
