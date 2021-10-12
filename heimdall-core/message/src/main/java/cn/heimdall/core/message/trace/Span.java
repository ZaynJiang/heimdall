package cn.heimdall.core.message.trace;

import java.util.List;

public class Span {
    private String type;
    private String name;
    private long costInMillis;
    private boolean completed;
    private List<Span> childrenSpan;
    private List<Event> events;
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

    public List<Span> getChildrenSpan() {
        return childrenSpan;
    }

    public void setChildrenSpan(List<Span> childrenSpan) {
        this.childrenSpan = childrenSpan;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public boolean isErrorTag() {
        return errorTag;
    }

    public void setErrorTag(boolean errorTag) {
        this.errorTag = errorTag;
    }
}
