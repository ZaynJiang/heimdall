package cn.heimdall.core.utils.window;

import cn.heimdall.core.utils.metric.MetricEvent;

import java.util.concurrent.atomic.LongAdder;

/**
 * 时间窗口桶
 */
public class WindowBucket {

    public static final int DEFAULT_STATISTIC_MAX_RT = 5000;

    private final LongAdder[] counters;

    private volatile long minRt;

    public WindowBucket() {
        MetricEvent[] events = MetricEvent.values();
        this.counters = new LongAdder[events.length];
        for (MetricEvent event : events) {
            counters[event.ordinal()] = new LongAdder();
        }
        initMinRt();
    }

    public WindowBucket reset(WindowBucket bucket) {
        for (MetricEvent event : MetricEvent.values()) {
            counters[event.ordinal()].reset();
            counters[event.ordinal()].add(bucket.get(event));
        }
        initMinRt();
        return this;
    }

    private void initMinRt() {
        this.minRt = DEFAULT_STATISTIC_MAX_RT;
    }

    /**
     * 重构收集器
     * @return
     */
    public WindowBucket reset() {
        for (MetricEvent event : MetricEvent.values()) {
            counters[event.ordinal()].reset();
        }
        initMinRt();
        return this;
    }

    public long get(MetricEvent event) {
        return counters[event.ordinal()].sum();
    }

    public WindowBucket add(MetricEvent event, long n) {
        counters[event.ordinal()].add(n);
        return this;
    }

    public long exception() {
        return get(MetricEvent.EXCEPTION);
    }

    public long rt() {
        return get(MetricEvent.RT);
    }

    public long minRt() {
        return minRt;
    }

    public long success() {
        return get(MetricEvent.SUCCESS);
    }

    public void addException(int n) {
        add(MetricEvent.EXCEPTION, n);
    }

    public void addSuccess(int n) {
        add(MetricEvent.SUCCESS, n);
    }

    public void addRT(long rt) {
        add(MetricEvent.RT, rt);

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
