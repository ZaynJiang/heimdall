package cn.heimdall.core.message.metric;

import cn.heimdall.core.utils.enums.MetricType;

import java.util.Objects;

public class EventMetricKey extends MetricKey {

    public EventMetricKey(String domain, String ip, String type, String name) {
        super(domain, ip, type, name);
    }

    @Override
    public MetricType getMetricType() {
        return MetricType.MetricTypeEvent;
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

}
