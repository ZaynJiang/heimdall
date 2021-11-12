package cn.heimdall.core.message.metric;

import cn.heimdall.core.message.window.AbstractWindowManager;
import cn.heimdall.core.message.window.BucketWindowManager;
import cn.heimdall.core.message.window.TimeWindow;
import cn.heimdall.core.message.window.WindowBucket;

import java.util.List;

public abstract class AbstractMetricInvoker implements Metric{
    protected final AbstractWindowManager<WindowBucket> data;

    public AbstractMetricInvoker(int sampleCount, int intervalInMs) {
        this.data = new BucketWindowManager(sampleCount, intervalInMs);
    }

    @Override
    public long getCount() {
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
    public void addCount(int count) {
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
}
