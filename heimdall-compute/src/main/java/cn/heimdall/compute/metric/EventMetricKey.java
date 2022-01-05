package cn.heimdall.compute.metric;

import cn.heimdall.core.message.metric.MetricKey;

import java.util.Objects;

public class EventMetricKey extends MetricKey {
    private String domain;
    private String ip;
    private String type;
    private String name;

    public EventMetricKey() {
    }

    public EventMetricKey(String domain, String ip, String type, String name) {
        this.domain = domain;
        this.ip = ip;
        this.type = type;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof EventMetricKey)) {
            return false;
        }
        EventMetricKey newObj = (EventMetricKey) obj;
        return Objects.equals(newObj.getMetricKey(), this.getMetricKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(domain, ip, type, name);
    }

    @Override
    public String toString() {
        return "EventMetricKey{" +
                "domain=" + domain +
                ", ip=" + ip +
                ", type=" + type +
                ", name=" + name +
                '}';
    }

    @Override
    public String getMetricKey() {
        return domain + ":" + ip + ":" + type + ":" + name;
    }


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
