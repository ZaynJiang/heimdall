package cn.heimdall.core.message.processor;

import cn.heimdall.core.message.body.MessageBody;
import cn.heimdall.core.message.body.MessageTreeBody;
import cn.heimdall.core.message.response.ProcessResult;

public class MessageTreeProcessor implements MessageProcessor {

    @Override
    public ProcessResult execute(MessageBody messageBody) {
        MessageTreeBody treeBody = (MessageTreeBody)messageBody;
        return null;
    }
}
