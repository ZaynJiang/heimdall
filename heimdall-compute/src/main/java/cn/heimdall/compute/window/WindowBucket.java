package cn.heimdall.compute.window;


import cn.heimdall.core.utils.constants.MetricConstants;
import cn.heimdall.compute.metric.EnumMetric;

import java.util.concurrent.atomic.LongAdder;

/**
 * 时间窗口桶
 */
public class WindowBucket {
    private final LongAdder[] counters;

    private volatile long minRt;

    public WindowBucket() {
        EnumMetric[] events = EnumMetric.values();
        this.counters = new LongAdder[events.length];
        for (EnumMetric event : events) {
            counters[event.ordinal()] = new LongAdder();
        }
        initMinRt();
    }

    public WindowBucket reset(WindowBucket bucket) {
        for (EnumMetric event : EnumMetric.values()) {
            counters[event.ordinal()].reset();
            counters[event.ordinal()].add(bucket.get(event));
        }
        initMinRt();
        return this;
    }

    private void initMinRt() {
        this.minRt = MetricConstants.DEFAULT_STATISTIC_MAX_RT;
    }

    /**
     * 重构收集器
     * @return
     */
    public WindowBucket reset() {
        for (EnumMetric event : EnumMetric.values()) {
            counters[event.ordinal()].reset();
        }
        initMinRt();
        return this;
    }

    public long get(EnumMetric event) {
        return counters[event.ordinal()].sum();
    }

    public WindowBucket add(EnumMetric event, long n) {
        counters[event.ordinal()].add(n);
        return this;
    }

    public long exception() {
        return get(EnumMetric.EXCEPTION);
    }

    public long rt() {
        return get(EnumMetric.RT);
    }

    public long minRt() {
        return minRt;
    }

    public long success() {
        return get(EnumMetric.SUCCESS);
    }

    public void addException(int n) {
        add(EnumMetric.EXCEPTION, n);
    }

    public void addSuccess(int n) {
        add(EnumMetric.SUCCESS, n);
    }

    public void addRT(long rt) {
        add(EnumMetric.RT, rt);
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
