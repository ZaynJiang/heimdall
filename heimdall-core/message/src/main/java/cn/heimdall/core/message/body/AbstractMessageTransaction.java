package cn.heimdall.core.message.body;

import cn.heimdall.core.message.MessageBody;

public abstract class AbstractMessageTransaction extends MessageBody {
   public abstract MessageResponse handle();
}
