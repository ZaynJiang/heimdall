package cn.heimdall.compute.analyzer.impl;

import cn.heimdall.core.message.MessageBody;

public interface Compute {
    void compute(MessageBody messageBody);
}
