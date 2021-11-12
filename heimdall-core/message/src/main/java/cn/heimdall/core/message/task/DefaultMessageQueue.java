package cn.heimdall.core.utils.task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class DefaultMessageQueue<T> implements MessageQueue<T>{

    private BlockingQueue<T> m_queue;

    public DefaultMessageQueue(int size) {
        m_queue = new ArrayBlockingQueue<T>(size);
    }

    @Override
    public boolean offer(T tree) {
        return m_queue.offer(tree);
    }

    @Override
    public T peek() {
        return m_queue.peek();
    }

    @Override
    public T poll() {
        try {
            return m_queue.poll(5, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return null;
        }
    }

    @Override
    public int size() {
        return m_queue.size();
    }

    @Override
    public boolean isEmpty() {
        return m_queue.isEmpty();
    }
}
