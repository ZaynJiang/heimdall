package cn.heimdall.core.config;

public interface Configuration {
    short getShort(String dataId, short defaultValue);
    int getInt(String dataId, int defaultValue);
    long getLong(String dataId, long defaultValue);
    boolean getBoolean(String dataId, boolean defaultValue);
    String getConfig(String dataId, String defaultValue);
    boolean putConfigIfAbsent(String dataId, String content);
    void addConfigListener(String dataId, ConfigurationChangeListener listener);
}
