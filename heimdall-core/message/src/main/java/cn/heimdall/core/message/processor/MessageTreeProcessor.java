package cn.heimdall.core.message.processor;

import cn.heimdall.core.message.body.MessageBody;
import cn.heimdall.core.message.compute.AbstractMessageAnalyzer;
import cn.heimdall.core.message.response.ProcessResult;

public class MessageTreeProcessor implements MessageProcessor {

    private AbstractMessageAnalyzer abstractMessageAnalyzer;

    @Override
    public ProcessResult execute(MessageBody messageBody) {
        abstractMessageAnalyzer.distribute(messageBody);
        return new ProcessResult();
    }
}
