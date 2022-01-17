package cn.heimdall.core.utils.enums;

import cn.heimdall.core.utils.constants.LoadLevelConstants;

public enum NodeRole {
    CLIENT(0, "client"),
    COMPUTE(1, LoadLevelConstants.COMPUTE_COORDINATOR),
    STORAGE(2, LoadLevelConstants.STORAGE_COORDINATOR),
    GUARDER(3, LoadLevelConstants.GUARDER_COORDINATOR),
    ACTION(4, LoadLevelConstants.ACTION_COORDINATOR);
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
