package cn.heimdall.compute.analyzer.compute;

import cn.heimdall.compute.metric.MetricKey;
import cn.heimdall.compute.metric.MetricWhatPulse;
import cn.heimdall.compute.metric.WhatPulse;
import cn.heimdall.core.message.trace.TraceLog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractMetricCompute extends AbstractCompute {

    private static volatile Map<MetricKey, MetricWhatPulse> whatPluses;

    public AbstractMetricCompute() {
        whatPluses = new ConcurrentHashMap<>();
    }

    protected MetricWhatPulse getMetricInvoker(MetricKey metricKey) {
        MetricWhatPulse metric = whatPluses.get(metricKey);
        if (metric != null) {
            return metric;
        }
        return whatPluses.putIfAbsent(metricKey, newMetric());
    }

    protected abstract void doInvokeMetric(TraceLog tracelog);

    protected abstract MetricWhatPulse newMetric();

    protected abstract MetricKey wrapMetricKey(TraceLog tracelog);

    public Map<MetricKey, MetricWhatPulse> getWhatPluses(){
        return whatPluses;
    }

    public static void resetClusterNodes() {
        whatPluses.values().stream().forEach(WhatPulse::reset);
    }
}
