package cn.heimdall.compute.window;


import cn.heimdall.core.config.constants.MessageConstants;
import cn.heimdall.compute.metric.MetricConstant;

import java.util.concurrent.atomic.LongAdder;

/**
 * 时间窗口桶
 */
public class WindowBucket {
    private final LongAdder[] counters;

    private volatile long minRt;

    public WindowBucket() {
        MetricConstant[] events = MetricConstant.values();
        this.counters = new LongAdder[events.length];
        for (MetricConstant event : events) {
            counters[event.ordinal()] = new LongAdder();
        }
        initMinRt();
    }

    public WindowBucket reset(WindowBucket bucket) {
        for (MetricConstant event : MetricConstant.values()) {
            counters[event.ordinal()].reset();
            counters[event.ordinal()].add(bucket.get(event));
        }
        initMinRt();
        return this;
    }

    private void initMinRt() {
        this.minRt = MessageConstants.DEFAULT_STATISTIC_MAX_RT;
    }

    /**
     * 重构收集器
     * @return
     */
    public WindowBucket reset() {
        for (MetricConstant event : MetricConstant.values()) {
            counters[event.ordinal()].reset();
        }
        initMinRt();
        return this;
    }

    public long get(MetricConstant event) {
        return counters[event.ordinal()].sum();
    }

    public WindowBucket add(MetricConstant event, long n) {
        counters[event.ordinal()].add(n);
        return this;
    }

    public long exception() {
        return get(MetricConstant.EXCEPTION);
    }

    public long rt() {
        return get(MetricConstant.RT);
    }

    public long minRt() {
        return minRt;
    }

    public long success() {
        return get(MetricConstant.SUCCESS);
    }

    public void addException(int n) {
        add(MetricConstant.EXCEPTION, n);
    }

    public void addSuccess(int n) {
        add(MetricConstant.SUCCESS, n);
    }

    public void addRT(long rt) {
        add(MetricConstant.RT, rt);
        //线程不安全，但无关紧要
        if (rt < minRt) {
            minRt = rt;
        }
    }

    @Override
    public String toString() {
        return "s: " + success() + ", e: " + exception() ;
    }
}
