package cn.heimdall.compute.metric;

import cn.heimdall.core.message.metric.MetricKey;
import cn.heimdall.core.message.metric.MetricNode;
import cn.heimdall.core.utils.common.CurrentTimeFactory;
import cn.heimdall.core.utils.constants.MetricConstants;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * 基于一个key的指标统计器
 */
public class MetricWhatPulse implements WhatPulse{
    //分钟级别的统计器
    private transient Metric rollingCounterInMinute = new ArrayMetric(MetricConstants.METRIC_SPAN_WINDOW_COUNT, MetricConstants.METRIC_SPAN_WINDOW_INTERVAL);
    private MetricKey metricKey;
    private LongAdder curThreadNum = new LongAdder();
    private long lastFetchTime = -1;

    public MetricWhatPulse(MetricKey metricKey) {
        this.metricKey = metricKey;
    }

    @Override
    public Map<Long, MetricNode> metrics() {
        // The fetch operation is thread-safe under a single-thread scheduler pool.
        long currentTime = CurrentTimeFactory.currentTimeMillis();
        currentTime = currentTime - currentTime % 1000;
        Map<Long, MetricNode> metrics = new ConcurrentHashMap<>();
        List<MetricNode> nodesOfEverySecond = rollingCounterInMinute.details();
        long newLastFetchTime = lastFetchTime;
        // Iterate metrics of all resources, filter valid metrics (not-empty and up-to-date).
        for (MetricNode node : nodesOfEverySecond) {
            if (isNodeInTime(node, currentTime) && isValidMetricNode(node)) {
                metrics.put(node.getTimestamp(), node);
                newLastFetchTime = Math.max(newLastFetchTime, node.getTimestamp());
            }
        }
        lastFetchTime = newLastFetchTime;

        return metrics;
    }

    @Override
    public void reset() {
        rollingCounterInMinute = new ArrayMetric(MetricConstants.METRIC_SPAN_WINDOW_COUNT,
                MetricConstants.METRIC_SPAN_WINDOW_INTERVAL);
    }


    private boolean isValidMetricNode(MetricNode node) {
        //TODO checksomething
        return node.getSuccessQps() > 0
                || node.getExceptionQps() > 0 || node.getRt() > 0;
    }

    private boolean isNodeInTime(MetricNode node, long currentTime) {
        return node.getTimestamp() > lastFetchTime && node.getTimestamp() < currentTime;
    }

    @Override
    public long getSuccess() {
        return rollingCounterInMinute.getSuccess();
    }

    @Override
    public long getExceptionCount() {
        return rollingCounterInMinute.getExceptionCount();
    }

    @Override
    public void addSuccess(int n) {
        rollingCounterInMinute.addSuccess(n);
    }

    @Override
    public void addException(int n) {
        rollingCounterInMinute.addException(n);
    }

    @Override
    public double getAvgRT() {
        long successCount = rollingCounterInMinute.getSuccess();
        if (successCount == 0) {
            return 0;
        }
        return rollingCounterInMinute.getTotalRT() * 1.0 / successCount;
    }

    @Override
    public long getMinRT() {
        return rollingCounterInMinute.getMinRT();
    }

    @Override
    public void addRT(long n) {
        rollingCounterInMinute.addRT(n);
    }

    public MetricKey getMetricKey() {
        return metricKey;
    }

    public void setMetricKey(MetricKey metricKey) {
        this.metricKey = metricKey;
    }
}
