package cn.heimdall.core.config.constants;

public enum ClientRole {
    COMPUTE(1),
    STORAGE(2),
    GUARDER(3),
    APP(4);
    private int value;

    ClientRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
