package cn.heimdall.core.message.body.client;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.MessageResponse;
import cn.heimdall.core.message.trace.EventLog;
import cn.heimdall.core.message.trace.SpanLog;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端形成的消息树
 */
public class MessageTreeRequest extends ClientMessageRequest {

    private ByteBuf byteBuf;

    private String messageId;

    private String parentMessageId;

    private String rootMessageId;

    private String threadGroupName;

    private String threadId;

    private String threadName;

    private boolean hitSample;

    /**
     * 事件记录
     */
    private List<EventLog> eventLogs = new ArrayList<EventLog>();
    /**
     * 跨度记录
     */
    private List<SpanLog> spanLogs = new ArrayList<SpanLog>();

    public ByteBuf getByteBuf() {
        return byteBuf;
    }

    public void setByteBuf(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getParentMessageId() {
        return parentMessageId;
    }

    public void setParentMessageId(String parentMessageId) {
        this.parentMessageId = parentMessageId;
    }

    public String getRootMessageId() {
        return rootMessageId;
    }

    public void setRootMessageId(String rootMessageId) {
        this.rootMessageId = rootMessageId;
    }

    public String getThreadGroupName() {
        return threadGroupName;
    }

    public void setThreadGroupName(String threadGroupName) {
        this.threadGroupName = threadGroupName;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public boolean isHitSample() {
        return hitSample;
    }

    public void setHitSample(boolean hitSample) {
        this.hitSample = hitSample;
    }

    public List<EventLog> getEventLogs() {
        return eventLogs;
    }

    public void setEventLogs(List<EventLog> eventLogs) {
        this.eventLogs = eventLogs;
    }

    public List<SpanLog> getSpanLogs() {
        return spanLogs;
    }

    public void setSpanLogs(List<SpanLog> spanLogs) {
        this.spanLogs = spanLogs;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_CLIENT_MESSAGE_TREE_REQUEST;
    }

    @Override
    public MessageResponse handle() {
        return computeInboundHandler.handle(this);
    }
}
