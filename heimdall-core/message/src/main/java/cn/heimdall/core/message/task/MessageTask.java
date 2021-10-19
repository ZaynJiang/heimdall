package cn.heimdall.core.message.compute;

import cn.heimdall.core.message.body.MessageBody;

public interface MessageTask extends Runnable{
     boolean offerQueue(MessageBody tree);
}
