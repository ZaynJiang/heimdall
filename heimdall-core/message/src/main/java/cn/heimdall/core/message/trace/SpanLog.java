package cn.heimdall.core.message.trace;

import java.util.List;

public class SpanLog extends TraceLog{
    private long startTime;
    private long endTime;
    private long costInMillis;
    private boolean completed;
    private List<SpanLog> childrenSpanLog;
    private List<EventLog> eventLogs;


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
