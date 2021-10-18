package cn.heimdall.core.message.schedule;

import cn.heimdall.core.message.compute.ComputeManager;
import cn.heimdall.core.message.compute.impl.SpanLogCompute;
import cn.heimdall.core.message.metric.Metric;
import cn.heimdall.core.message.metric.MetricKey;

import java.util.Map;

public class MetricTimerTask implements Runnable {
    @Override
    public void run() {
       //TODO 获取metric数据，然后flush至远程的storage
        SpanLogCompute spanCompute = (SpanLogCompute)ComputeManager.singleEventMetricCompute();
        Map<MetricKey, Metric> spanMetrics = spanCompute.getMetrics();

    }
}
