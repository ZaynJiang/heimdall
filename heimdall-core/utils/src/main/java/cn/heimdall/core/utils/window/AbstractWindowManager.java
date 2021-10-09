package cn.heimdall.core.utils.window;

import cn.heimdall.core.utils.common.AssertUtil;
import cn.heimdall.core.utils.common.CurrentTimeFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractWindowManager<T> {
    /**
     * 滑动时间窗口的大小（毫秒）
     */
    protected int windowLengthInMs;
    /**
     * 滑动窗口的个数
     */
    protected int sampleCount;
    /**
     * 统计时间的区间
     */
    protected int intervalInMs;

    private double intervalInSecond;

    /**
     * 统计数据保存数组
     */
    protected final AtomicReferenceArray<TimeWindow<T>> timeWindows;

    private final ReentrantLock updateLock = new ReentrantLock();

    public AbstractWindowManager(int sampleCount, int intervalInMs) {
        AssertUtil.isTrue(intervalInMs > 0, "intervalInMs is invalid: " + intervalInMs);
        AssertUtil.isTrue(sampleCount > 0, "bucket count is invalid: " + sampleCount);
        AssertUtil.isTrue(intervalInMs % sampleCount == 0, "time span divided is invalid");
        this.windowLengthInMs = intervalInMs / sampleCount;
        this.intervalInMs = intervalInMs;
        this.intervalInSecond = intervalInMs / 1000.0;
        this.sampleCount = sampleCount;
        this.timeWindows = new AtomicReferenceArray<>(sampleCount);
    }

    public TimeWindow<T> currentWindow() {
        return currentWindow(CurrentTimeFactory.currentTimeMillis());
    }

    public TimeWindow<T> currentWindow(long timeMillis) {
        if (timeMillis < 0) {
            return null;
        }
        int timeId = calculateTimeIdx(timeMillis);
        long timeStart = calculateWindowStart(timeMillis);
        TimeWindow<T> oldWindow = timeWindows.get(timeId);
        if (oldWindow == null) {
            TimeWindow<T> window = new TimeWindow<T>(windowLengthInMs, timeStart, newEmptyBucket(timeMillis));
            if (timeWindows.compareAndSet(timeId, null, window)) {
                return window;
            } else {
                Thread.yield();
            }
            //和当前一致
        } else if (oldWindow.windowStart() == timeStart) {
            return oldWindow;
            //当前时间早了,创建一个对应的时间窗口
        } else if (oldWindow.windowStart() > timeStart) {
            return new TimeWindow<>(timeMillis, timeStart, newEmptyBucket(timeMillis));
        } else if (oldWindow.windowStart() < timeStart) {
            if (updateLock.tryLock()) {
                try {
                    return resetWindowTo(oldWindow, timeStart);
                } finally {
                    updateLock.unlock();
                }
            } else {
                Thread.yield();
            }
        }
        return oldWindow;
    }

    /**
     * 获取当前时间的上一个时间窗口
     * @return
     */
    public TimeWindow<T> getPreviousWindow() {
        return getPreviousWindow(CurrentTimeFactory.currentTimeMillis());
    }

    public TimeWindow<T> getPreviousWindow(long timeMillis) {
        if (timeMillis < 0) {
            return null;
        }
        int idx = calculateTimeIdx(timeMillis - windowLengthInMs);
        timeMillis = timeMillis - windowLengthInMs;
        TimeWindow<T> window = timeWindows.get(idx);
        if (window == null || isWindowDeprecated(window)) {
            return null;
        }
        if (window.windowStart() + windowLengthInMs < (timeMillis)) {
            return null;
        }
        return window;
    }

    public List<T> values() {
        return values(CurrentTimeFactory.currentTimeMillis());
    }

    public List<T> values(long timeMillis) {
        if (timeMillis < 0) {
            return new ArrayList<T>();
        }
        int size = timeWindows.length();
        List<T> result = new ArrayList<T>(size);

        for (int i = 0; i < size; i++) {
            TimeWindow<T> timeWindow = timeWindows.get(i);
            if (timeWindow == null || isWindowDeprecated(timeMillis, timeWindow)) {
                continue;
            }
            result.add(timeWindow.value());
        }
        return result;
    }

    /**
     * 获取当前时间的所有时间窗口桶
     * @return
     */
    public List<TimeWindow<T>> list() {
        return list(CurrentTimeFactory.currentTimeMillis());
    }

    public List<TimeWindow<T>> list(long validTime) {
        int size = timeWindows.length();
        List<TimeWindow<T>> result = new ArrayList<TimeWindow<T>>(size);
        for (int i = 0; i < size; i++) {
            TimeWindow<T> timeWindow = timeWindows.get(i);
            if (timeWindow == null || isWindowDeprecated(validTime, timeWindow)) {
                continue;
            }
            result.add(timeWindow);
        }
        return result;
    }

    /**
     * 获取所有时间窗口桶
     * @return
     */
    public List<TimeWindow<T>> listAll() {
        int size = timeWindows.length();
        List<TimeWindow<T>> result = new ArrayList<TimeWindow<T>>(size);
        for (int i = 0; i < size; i++) {
            TimeWindow<T> timeWindow = timeWindows.get(i);
            if (timeWindow == null) {
                continue;
            }
            result.add(timeWindow);
        }

        return result;
    }

    /**
     * 重置时间窗口
     * @param timeWindow
     * @param startTime
     * @return
     */
    protected abstract TimeWindow<T> resetWindowTo(TimeWindow<T> timeWindow, long startTime);

    /**
     * 创建一个时间窗口对应的桶
     * @param timeMillis
     * @return
     */
    public abstract T newEmptyBucket(long timeMillis);


    private boolean isWindowDeprecated(TimeWindow<T> timeWindow) {
        return isWindowDeprecated(CurrentTimeFactory.currentTimeMillis(), timeWindow);
    }

    private boolean isWindowDeprecated(long time, TimeWindow<T> timeWindow) {
        return time - timeWindow.windowStart() > intervalInMs;
    }

    private int calculateTimeIdx(long timeMillis) {
        //计算当前时间是第timeId份区间。
        long timeId = timeMillis / windowLengthInMs;
        //计算第timeId区间在当前数组是第几个
        return (int)(timeId % timeWindows.length());
    }

    private long calculateWindowStart(long timeMillis) {
        return timeMillis - timeMillis % windowLengthInMs;
    }
}
