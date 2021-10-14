package cn.heimdall.core.message.compute.impl;

import cn.heimdall.core.message.metric.Metric;
import cn.heimdall.core.message.metric.MetricKey;
import cn.heimdall.core.message.trace.TraceLog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTraceLogCompute extends AbstractCompute {

    private Map<MetricKey, Metric> metrics;

    public AbstractTraceLogCompute() {
        metrics = new ConcurrentHashMap<>();
    }

    protected Metric getMetricInvoker(MetricKey metricKey) {
        Metric metric = metrics.get(metricKey);
        if (metric != null) {
            return metric;
        }
        return metrics.putIfAbsent(metricKey, newMetric());
    }

    protected abstract void doInvokeMetric(TraceLog tracelog);

    protected abstract Metric newMetric();

    protected abstract MetricKey wrapMetricKey(TraceLog tracelog);
}
