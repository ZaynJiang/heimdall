package cn.heimdall.core.utils.common;

import java.util.concurrent.TimeUnit;

public class CurrentTimeFactory {

    private static volatile long currentTimeMillis;

    static {
        currentTimeMillis = System.currentTimeMillis();
        Thread daemon = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    currentTimeMillis = System.currentTimeMillis();
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (Throwable e) {

                    }
                }
            }
        });
        daemon.setDaemon(true);
        daemon.setName("heimdall-server-currentTime-thread");
        daemon.start();
    }

    public static long currentTimeMillis() {
        return currentTimeMillis;
    }
}
