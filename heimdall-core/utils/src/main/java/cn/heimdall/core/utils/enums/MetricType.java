package cn.heimdall.core.utils.enums;

public enum MetricType {
    MetricTypeSpan("span"),
    MetricTypeEvent("event");

    private String name;

    MetricType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
