package cn.heimdall.core.message.compute.impl;

import cn.heimdall.core.message.body.HeartbeatBody;
import cn.heimdall.core.message.body.MessageBody;
import cn.heimdall.core.message.metric.Metric;
import cn.heimdall.core.message.metric.MetricKey;

import java.util.Map;

public class HeartbeatCompute extends AbstractCompute {
    @Override
    public void compute(MessageBody messageBody) {
        HeartbeatBody heartbeatBody = (HeartbeatBody) messageBody;
        //todo
    }
}
