package cn.heimdall.compute.analyzer;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageTypeAware;

public interface MessageAnalyzer extends MessageTypeAware {
     void init();
     void distribute(MessageBody messageBody);
}
