package cn.heimdall.compute.metric;

public abstract class MetricKey {
    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    public abstract String getMetricKey();
}
