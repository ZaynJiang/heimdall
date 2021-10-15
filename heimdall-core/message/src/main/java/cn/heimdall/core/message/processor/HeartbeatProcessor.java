package cn.heimdall.core.message.processor;


import cn.heimdall.core.message.body.MessageBody;
import cn.heimdall.core.message.compute.AbstractMessageAnalyzer;
import cn.heimdall.core.message.response.ProcessResult;

public class HeartbeatProcessor implements MessageProcessor {

    //TODO 初始化
    private AbstractMessageAnalyzer messageAnalyzer;

    @Override
    public ProcessResult execute(MessageBody messageBody) {
        return null;
    }
}
