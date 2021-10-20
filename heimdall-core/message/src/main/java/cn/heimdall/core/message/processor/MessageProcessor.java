package cn.heimdall.core.message.processor;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.response.ProcessResult;

public interface MessageProcessor {
   ProcessResult execute(MessageBody messageBody);
}
