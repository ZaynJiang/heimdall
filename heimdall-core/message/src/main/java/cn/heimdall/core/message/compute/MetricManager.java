package cn.heimdall.core.message.compute;

import cn.heimdall.core.message.constants.MessageConstants;
import cn.heimdall.core.message.metric.MetricKey;
import cn.heimdall.core.message.metric.SpanMetric;
import cn.heimdall.core.message.metric.SpanMetricCompute;
import cn.heimdall.core.message.metric.SpanMetricKey;
import cn.heimdall.core.message.trace.Span;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MetricManager {
    //metricKey: domain:key
    private Map<MetricKey, SpanMetric> spanMetrics;

    public MetricManager() {
        spanMetrics = new ConcurrentHashMap<>();
    }

    private SpanMetric getSpanMetric(MetricKey metricKey) {
        SpanMetric spanMetricCompute = spanMetrics.get(metricKey);
        if (spanMetricCompute != null) {
            return spanMetricCompute;
        }
        return spanMetrics.putIfAbsent(metricKey,
                new SpanMetricCompute(MessageConstants.METRIC_SPAN_WINDOW_INTERVAL, MessageConstants.METRIC_SPAN_WINDOW_COUNT));
    }

    public void invokeSpanMetric(SpanMetricKey metricKey, Span span){
        SpanMetric spanMetric = this.getSpanMetric(metricKey);
        spanMetric.addRT(span.getCostInMillis());
        spanMetric.addCount(1);
        if (span.isErrorTag()) {
            spanMetric.addException(1);
        }
    }
}
