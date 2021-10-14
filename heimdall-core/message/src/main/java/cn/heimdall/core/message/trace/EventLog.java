package cn.heimdall.core.message.trace;

public class EventLog extends TraceLog{
    private String type;
    private String name;
    private String content;
    private boolean errorTag;

    public boolean isErrorTag() {
        return errorTag;
    }

    public void setErrorTag(boolean errorTag) {
        this.errorTag = errorTag;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
