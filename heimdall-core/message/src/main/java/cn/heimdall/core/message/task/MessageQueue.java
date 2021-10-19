package cn.heimdall.core.message.compute;

public interface MessageQueue<T> {
    boolean offer(T element);
    T peek();
    T poll();
    int size();
    boolean isEmpty();
}
