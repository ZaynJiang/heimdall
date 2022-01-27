package cn.heimdall.core.utils.enums;

import cn.heimdall.core.utils.constants.LoadLevelConstants;

public enum NodeRole {
    CLIENT(0, "client"),
    COMPUTE(1, LoadLevelConstants.COORDINATOR_COMPUTE),
    STORAGE(2, LoadLevelConstants.COORDINATOR_STORAGE),
    GUARDER(3, LoadLevelConstants.COORDINATOR_GUARDER),
    ACTION(4, LoadLevelConstants.COORDINATOR_ACTION);
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
