package cn.heimdall.compute.window;

public class TimeWindow<T> {

    /**
     * 时间窗口区间
     */
    private final long windowLengthInMs;

    /**
     * 时间窗口起始时间
     */
    private long windowStart;

    /**
     * 时间窗口值
     */
    private T value;

    public TimeWindow(long windowLengthInMs, long windowStart, T value) {
        this.windowLengthInMs = windowLengthInMs;
        this.windowStart = windowStart;
        this.value = value;
    }

    public long windowLength() {
        return windowLengthInMs;
    }

    public long windowStart() {
        return windowStart;
    }

    public T value() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public TimeWindow<T> resetTo(long startTime) {
        this.windowStart = startTime;
        return this;
    }

    public boolean isTimeInWindow(long timeMillis) {
        return windowStart <= timeMillis && timeMillis < windowStart + windowLengthInMs;
    }

    @Override
    public String toString() {
        return "TimeWindow{" +
                "windowLengthInMs=" + windowLengthInMs +
                ", windowStart=" + windowStart +
                ", value=" + value +
                '}';
    }
}
