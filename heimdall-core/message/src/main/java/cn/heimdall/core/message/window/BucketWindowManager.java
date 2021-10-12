package cn.heimdall.core.message.window;

public class BucketWindowManager extends AbstractWindowManager<WindowBucket> {
    public BucketWindowManager(int sampleCount, int intervalInMs) {
        super(sampleCount, intervalInMs);
    }

    @Override
    protected TimeWindow<WindowBucket> resetWindowTo(TimeWindow<WindowBucket> timeWindow, long startTime) {
        timeWindow.resetTo(startTime);
        timeWindow.value().reset();
        return timeWindow;
    }

    @Override
    public WindowBucket newEmptyBucket(long timeMillis) {
        return new WindowBucket();
    }
}
