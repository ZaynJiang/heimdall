package cn.heimdall.compute.processor;


import cn.heimdall.core.message.MessageBody;
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
