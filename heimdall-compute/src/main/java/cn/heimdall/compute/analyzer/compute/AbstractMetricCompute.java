package cn.heimdall.compute.analyzer.compute;

import cn.heimdall.core.message.metric.MetricKey;
import cn.heimdall.compute.metric.MetricWhatPulse;
import cn.heimdall.core.message.trace.TraceLog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractMetricCompute extends AbstractCompute {

    private static volatile Map<MetricKey, MetricWhatPulse> whatPluses = new ConcurrentHashMap<>();

    protected abstract void doInvokeMetric(TraceLog tracelog);

    protected MetricWhatPulse getMetricInvoker(MetricKey metricKey) {
        MetricWhatPulse metric = whatPluses.get(metricKey);
        if (metric != null) {
            return metric;
        }
        return whatPluses.putIfAbsent(metricKey, new MetricWhatPulse(metricKey));
    }

    protected abstract MetricKey wrapMetricKey(TraceLog tracelog);

    public static Map<MetricKey, MetricWhatPulse> getWhatPluses(){
        return whatPluses;
    }
}
