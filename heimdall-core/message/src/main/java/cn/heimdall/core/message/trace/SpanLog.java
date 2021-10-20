package cn.heimdall.core.message.trace;

import java.util.List;

public class SpanLog extends TraceLog{
    private String type;
    private String name;
    private String content;
    private long startTime;
    private long endTime;
    private long costInMillis;
    private boolean completed;
    private List<SpanLog> childrenSpanLog;
    private List<EventLog> eventLogs;
    private boolean errorTag;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCostInMillis() {
        return costInMillis;
    }

    public void setCostInMillis(long costInMillis) {
        this.costInMillis = costInMillis;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<SpanLog> getChildrenSpanLog() {
        return childrenSpanLog;
    }

    public void setChildrenSpanLog(List<SpanLog> childrenSpanLog) {
        this.childrenSpanLog = childrenSpanLog;
    }

    public List<EventLog> getEventLogs() {
        return eventLogs;
    }

    public void setEventLogs(List<EventLog> eventLogs) {
        this.eventLogs = eventLogs;
    }

    public boolean isErrorTag() {
        return errorTag;
    }

    public void setErrorTag(boolean errorTag) {
        this.errorTag = errorTag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
