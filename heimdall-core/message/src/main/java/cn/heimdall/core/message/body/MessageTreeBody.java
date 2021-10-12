package cn.heimdall.core.message.body;

import cn.heimdall.core.message.trace.Event;
import cn.heimdall.core.message.trace.Span;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端形成的消息树
 */
public class MessageTreeBody extends MessageBody{

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
    private List<Event> events = new ArrayList<Event>();
    /**
     * 跨度记录
     */
    private List<Span> spans = new ArrayList<Span>();

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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Span> getSpans() {
        return spans;
    }

    public void setSpans(List<Span> spans) {
        this.spans = spans;
    }
}
