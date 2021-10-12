package cn.heimdall.core.message.compute;

import cn.heimdall.core.message.body.MessageBody;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class DefaultMessageQueue implements MessageQueue{

    private BlockingQueue<MessageBody> m_queue;

    public DefaultMessageQueue(int size) {
        m_queue = new ArrayBlockingQueue<MessageBody>(size);
    }

    @Override
    public boolean offer(MessageBody tree) {
        return m_queue.offer(tree);
    }

    @Override
    public MessageBody peek() {
        return m_queue.peek();
    }

    @Override
    public MessageBody poll() {
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
