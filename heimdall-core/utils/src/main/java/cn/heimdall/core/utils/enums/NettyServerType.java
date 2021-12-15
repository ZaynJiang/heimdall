package cn.heimdall.core.utils.enums;

public enum NettyServerType {
    //管理类
    MANAGE(1, "manage"),
    //数据传输类
    TRANSPORT(2, "transport"),
    //应用类
    ACTION(3, "action");
    private String name;
    private int value;

    NettyServerType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
