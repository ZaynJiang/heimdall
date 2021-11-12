package cn.heimdall.compute.analyzer.impl;

import cn.heimdall.core.message.body.HeartbeatBody;
import cn.heimdall.core.message.MessageBody;

public class HeartbeatCompute extends AbstractCompute {
    @Override
    public void compute(MessageBody messageBody) {
        HeartbeatBody heartbeatBody = (HeartbeatBody) messageBody;
        //todo
    }
}
