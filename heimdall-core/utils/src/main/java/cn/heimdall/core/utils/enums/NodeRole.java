package cn.heimdall.core.utils.enums;

public enum NodeRole {
    CLIENT(0, "client"),
    COMPUTE(1, "compute"),
    STORAGE(2, "storage"),
    GUARDER(3, "guarder");
    private String name;
    private int value;

    NodeRole(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
