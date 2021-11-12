package cn.heimdall.core.utils.task;

import cn.heimdall.core.message.MessageBody;

public interface MessageTask extends Runnable{
     boolean offerQueue(MessageBody tree);
}
