package cn.heimdall.core.message.schedule;

import cn.heimdall.core.utils.thread.NamedThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MetricScheduleManager {
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1,
            new NamedThreadFactory("compute-metrics-storage-task", true));

    static {
        SCHEDULER.scheduleAtFixedRate(new MetricTimerTask(), 0, 1, TimeUnit.SECONDS);
    }
}
