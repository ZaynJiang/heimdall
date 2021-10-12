package cn.heimdall.core.message.metric;

public class EventMetricCompute extends AbstractMetric implements EventMetric{

    public EventMetricCompute(int sampleCount, int intervalInMs) {
        super(sampleCount, intervalInMs);
    }
}
