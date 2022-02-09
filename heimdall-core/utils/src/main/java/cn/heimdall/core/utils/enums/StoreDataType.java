package cn.heimdall.core.utils.enums;

import cn.heimdall.core.utils.constants.LoadLevelConstants;

public enum StoreDataType {
    STORE_DATA_TYPE_APP_STATE(LoadLevelConstants.STORE_DATA_TYPE_APP_STATE),
    STORE_DATA_TYPE_METRIC(LoadLevelConstants.STORE_DATA_TYPE_METRIC),
    STORE_DATA_TYPE_TRACE(LoadLevelConstants.STORE_DATA_TYPE_TRACE);

    private String name;

    StoreDataType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}