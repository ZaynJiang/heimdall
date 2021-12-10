package cn.heimdall.core.network.coordinator;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.network.processor.ServerProcessor;

import java.util.Map;

public interface Coordinator {
    Map<MessageType, Class<? extends ServerProcessor>> getServerProcessors();
}
