package cn.heimdall.core.message.body.store;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.trace.TraceLog;

import java.util.List;

/**
 * 存储tracelog请求body
 */
public class StoreTraceRequest extends AbstractStoreRequest {

    private List<TraceLog> eventLogs;

    private List<TraceLog> spanLogs;

    @Override
    public MessageType getMessageType() {
        return null;
    }
}
