package cn.heimdall.core.message.processor;


import cn.heimdall.core.message.body.MessageBody;
import cn.heimdall.core.message.response.ProcessResult;

public class HeartbeatProcessor implements MessageProcessor {

    @Override
    public ProcessResult execute(MessageBody messageBody) {
        return null;
    }
}
