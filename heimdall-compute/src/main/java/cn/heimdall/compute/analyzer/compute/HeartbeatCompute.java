package cn.heimdall.compute.analyzer.compute;

import cn.heimdall.core.message.body.client.AppStateRequest;
import cn.heimdall.core.message.MessageBody;

public class HeartbeatCompute extends AbstractCompute {
    @Override
    public void compute(MessageBody messageBody) {
        AppStateRequest appStateRequest = (AppStateRequest) messageBody;
        //todo
    }
}
