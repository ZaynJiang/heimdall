package cn.heimdall.compute.processor;

import cn.heimdall.core.message.MessageHeader;
import cn.heimdall.core.message.request.RequestMessage;
import cn.heimdall.core.message.response.ProcessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ProcessorManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorManager.class);
    protected final Map<Short, MessageProcessor> processors = new HashMap<>(32);
    private void registerProcessor(short typeCode, MessageProcessor processor) {
        processors.put(typeCode, processor);
    }

    public ProcessResult processResult(RequestMessage requestMessage){
        MessageHeader messageHeader = requestMessage.getMessageHeader();
        MessageProcessor processor = processors.get(messageHeader.getTypeCode());
        return processor.execute(requestMessage.getMessageBody());
    }

}
