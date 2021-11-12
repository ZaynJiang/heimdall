package cn.heimdall.compute.metric;

public class EventMetricInvoker extends AbstractMetricInvoker implements EventMetric{

    public EventMetricInvoker(int sampleCount, int intervalInMs) {
        super(sampleCount, intervalInMs);
    }
}
