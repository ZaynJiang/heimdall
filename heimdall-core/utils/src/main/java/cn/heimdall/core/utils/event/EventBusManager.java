package cn.heimdall.core.utils.event;

public class EventBusManager {
    private static class SingletonHolder {
        private static EventBus INSTANCE = new GuavaEventBus("heimdall");
    }

    public static EventBus get() {
        return SingletonHolder.INSTANCE;
    }
}
