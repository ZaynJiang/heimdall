package cn.heimdall.core.message.compute.impl;

import cn.heimdall.core.message.body.HeartbeatBody;
import cn.heimdall.core.message.body.MessageBody;

public class HeartbeatCompute extends AbstractCompute {
    @Override
    public void compute(MessageBody messageBody) {
        HeartbeatBody heartbeatBody = (HeartbeatBody) messageBody;
        //todo
    }
}
