package cn.heimdall.core.message.compute;

import cn.heimdall.core.message.body.MessageBody;

public interface MessageQueue {
    boolean offer(MessageBody messageBody);
    MessageBody peek();
    MessageBody poll();
    int size();
    boolean isEmpty();
}
