package cn.heimdall.core.message.metric;

import cn.heimdall.core.utils.enums.MetricType;

import java.util.Objects;

public class SpanMetricKey extends MetricKey {
    private String domain;
    private String ip;
    private String type;
    private String name;

    public SpanMetricKey(String domain, String ip, String type, String name) {
        super(domain, ip, type, name);
    }

    @Override
    public MetricType getMetricType() {
        return MetricType.MetricTypeSpan;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof SpanMetricKey)) {
            return false;
        }
        SpanMetricKey newObj = (SpanMetricKey) obj;
        return Objects.equals(newObj.getMetricKey(), this.getMetricKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(domain, ip, type, name);
    }

    @Override
    public String toString() {
        return "SpanMetricKey{" +
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
