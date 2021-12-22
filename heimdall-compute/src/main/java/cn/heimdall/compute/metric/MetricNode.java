package cn.heimdall.compute.metric;

public class MetricNode {
    private MetricKey metricKey;
    private long successQps;
    private long exceptionQps;
    private long rt;
    private long timestamp;

    public MetricKey getMetricKey() {
        return metricKey;
    }

    public void setMetricKey(MetricKey metricKey) {
        this.metricKey = metricKey;
    }

    public long getSuccessQps() {
        return successQps;
    }

    public void setSuccessQps(long successQps) {
        this.successQps = successQps;
    }

    public long getExceptionQps() {
        return exceptionQps;
    }

    public void setExceptionQps(long exceptionQps) {
        this.exceptionQps = exceptionQps;
    }

    public long getRt() {
        return rt;
    }

    public void setRt(long rt) {
        this.rt = rt;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MetricNode{" +
                "metricKey=" + metricKey +
                ", successQps=" + successQps +
                ", exceptionQps=" + exceptionQps +
                ", rt=" + rt +
                ", timestamp=" + timestamp +
                '}';
    }
}
