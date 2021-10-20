package cn.heimdall.core.message.compute.impl;

import cn.heimdall.core.message.MessageBody;

public interface Compute {
    void compute(MessageBody messageBody);
}
