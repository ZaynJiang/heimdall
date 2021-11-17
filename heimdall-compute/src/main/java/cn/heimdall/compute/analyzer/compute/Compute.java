package cn.heimdall.compute.analyzer.compute;

import cn.heimdall.core.message.MessageBody;

public interface Compute {
    void compute(MessageBody messageBody);
}
