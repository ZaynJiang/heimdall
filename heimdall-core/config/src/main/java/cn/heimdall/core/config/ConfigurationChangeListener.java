package cn.heimdall.core.config;

import cn.heimdall.core.utils.thread.NamedThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class ConfigurationChangeListener {
    int CORE_LISTENER_THREAD = 1;
    int MAX_LISTENER_THREAD = 1;
    ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(CORE_LISTENER_THREAD, MAX_LISTENER_THREAD,
            Integer.MAX_VALUE, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
            new NamedThreadFactory("configListenerOperate", MAX_LISTENER_THREAD));

    public void onProcessEvent(ConfigurationChangeEvent event) {
        EXECUTOR_SERVICE.submit(() -> {
            onChangeEvent(event);
        });
    }

    public void onShutDown() {
        EXECUTOR_SERVICE.shutdown();
    }


    public abstract void onChangeEvent(ConfigurationChangeEvent event);

}
