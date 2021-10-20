package cn.heimdall.core.message.processor;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.compute.AbstractMessageAnalyzer;
import cn.heimdall.core.message.response.ProcessResult;

public class MessageTreeProcessor implements MessageProcessor {

    //TODO 初始化
    private AbstractMessageAnalyzer abstractMessageAnalyzer;

    @Override
    public ProcessResult execute(MessageBody messageBody) {
        abstractMessageAnalyzer.distribute(messageBody);
        //TODO 返回需要注意计算节点的状态是否正常
        return new ProcessResult();
    }
}
