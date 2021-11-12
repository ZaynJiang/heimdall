package cn.heimdall.core.utils.task;

public interface MessageQueue<T> {
    boolean offer(T element);
    T peek();
    T poll();
    int size();
    boolean isEmpty();
}
