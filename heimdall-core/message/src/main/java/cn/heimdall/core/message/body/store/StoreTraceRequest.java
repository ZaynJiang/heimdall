package cn.heimdall.core.message.body.store;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.RpcMessage;
import cn.heimdall.core.message.body.MessageResponse;
import cn.heimdall.core.message.trace.EventLog;
import cn.heimdall.core.message.trace.SpanLog;

import java.util.List;

/**
 * 存储tracelog请求body
 */
public class StoreTraceRequest extends AbstractStoreRequest {

    private List<SpanLog> spanLogs;
    private List<EventLog> eventLogs;

    public List<SpanLog> getSpanLogs() {
        return spanLogs;
    }

    public void setSpanLogs(List<SpanLog> spanLogs) {
        this.spanLogs = spanLogs;
    }

    public List<EventLog> getEventLogs() {
        return eventLogs;
    }

    public void setEventLogs(List<EventLog> eventLogs) {
        this.eventLogs = eventLogs;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_STORE_TRANCE_LOG_REQUEST;
    }

    @Override
    public MessageResponse handle() {
        return null;
    }

    public static RpcMessage getRpcMessage(List<SpanLog> spanLogs, List<EventLog> eventLogs) {
        StoreTraceRequest request = new StoreTraceRequest();
        request.setEventLogs(eventLogs);
        request.setSpanLogs(spanLogs);
        return new RpcMessage(request);
    }

}
