package cn.heimdall.core.utils.event;


public interface EventBus {
    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Event event);
}
