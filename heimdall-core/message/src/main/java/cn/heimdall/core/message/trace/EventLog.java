package cn.heimdall.core.message.trace;

public class EventLog extends TraceLog{

    private long eventTime;
    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }
}
