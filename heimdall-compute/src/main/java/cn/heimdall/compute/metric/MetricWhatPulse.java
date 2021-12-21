package cn.heimdall.compute.metric;

import java.util.concurrent.atomic.LongAdder;

/**
 * 基于一个key的指标统计器
 */
public class MetricWhatPulse {
    private transient Metric rollingCounterInMinute = new SpanMetricInvoker(60, 60 * 1000);
    private MetricKey metricKey;
    private LongAdder curThreadNum = new LongAdder();
}
