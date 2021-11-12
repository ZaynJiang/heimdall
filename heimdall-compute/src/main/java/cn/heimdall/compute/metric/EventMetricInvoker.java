package cn.heimdall.core.message.metric;

public class EventMetricInvoker extends AbstractMetricInvoker implements EventMetric{

    public EventMetricInvoker(int sampleCount, int intervalInMs) {
        super(sampleCount, intervalInMs);
    }
}
