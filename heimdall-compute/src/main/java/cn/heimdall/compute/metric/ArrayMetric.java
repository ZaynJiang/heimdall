package cn.heimdall.compute.metric;

import cn.heimdall.compute.window.AbstractWindowManager;
import cn.heimdall.compute.window.BucketWindowManager;
import cn.heimdall.compute.window.TimeWindow;
import cn.heimdall.compute.window.WindowBucket;
import cn.heimdall.core.message.metric.MetricNode;
import cn.heimdall.core.utils.constants.MetricConstants;

import java.util.ArrayList;
import java.util.List;

public class ArrayMetric implements Metric{
    protected final AbstractWindowManager<WindowBucket> data;

    public ArrayMetric(int sampleCount, int intervalInMs) {
        this.data = new BucketWindowManager(sampleCount, intervalInMs);
    }

    @Override
    public long getSuccess() {
        data.currentWindow();
        long success = 0;
        List<WindowBucket> list = data.values();
        for (WindowBucket window : list) {
            success += window.success();
        }
        return success;
    }

    @Override
    public long getExceptionCount() {
        data.currentWindow();
        long exception = 0;
        List<WindowBucket> list = data.values();
        for (WindowBucket window : list) {
            exception += window.exception();
        }
        return exception;
    }

    @Override
    public void addSuccess(int count) {
        TimeWindow<WindowBucket> wrap = data.currentWindow();
        wrap.value().addSuccess(count);
    }

    @Override
    public void addException(int count) {
        TimeWindow<WindowBucket> wrap = data.currentWindow();
        wrap.value().addException(count);
    }

    @Override
    public WindowBucket[] windows() {
        data.currentWindow();
        return data.values().toArray(new WindowBucket[0]);
    }

    @Override
    public long getTotalRT() {
        data.currentWindow();
        long rt = 0;
        List<WindowBucket> list = data.values();
        for (WindowBucket window : list) {
            rt += window.rt();
        }
        return rt;
    }

    @Override
    public long getMinRT() {
        data.currentWindow();
        long rt = MetricConstants.DEFAULT_STATISTIC_MAX_RT;
        List<WindowBucket> list = data.values();
        for (WindowBucket window : list) {
            if (window.minRt() < rt) {
                rt = window.minRt();
            }
        }
        return Math.max(1, rt);
    }

    @Override
    public void addRT(long rt) {
        TimeWindow<WindowBucket> wrap = data.currentWindow();
        wrap.value().addRT(rt);
    }

    @Override
    public List<MetricNode> details() {
        List<MetricNode> details = new ArrayList<>();
        data.currentWindow();
        List<TimeWindow<WindowBucket>> list = data.list();
        for (TimeWindow<WindowBucket> window : list) {
            if (window == null) {
                continue;
            }
            details.add(wrapMetricNode(window));
        }

        return details;
    }


    private MetricNode wrapMetricNode(TimeWindow<WindowBucket> wrap) {
        MetricNode node = new MetricNode();
        long successQps = wrap.value().success();
        node.setSuccessQps(successQps);
        if (successQps != 0) {
            node.setRt(wrap.value().rt() / successQps);
        } else {
            node.setRt(wrap.value().rt());
        }
        node.setTimestamp(wrap.windowStart());
        return node;
    }
}
