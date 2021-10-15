package cn.heimdall.core.message.metric;

import cn.heimdall.core.message.window.TimeWindow;
import cn.heimdall.core.message.window.WindowBucket;

import java.util.List;

public class SpanMetricInvoker extends AbstractMetricInvoker implements SpanMetric{

    public SpanMetricInvoker(int sampleCount, int intervalInMs) {
        super(sampleCount, intervalInMs);
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
        long rt = MessageConstants.DEFAULT_STATISTIC_MAX_RT;
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
}
