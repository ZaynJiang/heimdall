package cn.heimdall.core.config;

import java.util.Set;

/**
 * 配置
 */
public interface Configuration {
    short getShort(String configId, short defaultValue);
    int getInt(String configId, int defaultValue);
    long getLong(String configId, long defaultValue);
    boolean getBoolean(String configId, boolean defaultValue);
    String getConfig(String configId, String defaultValue);
    boolean putConfigIfAbsent(String configId, String content);
    String getLatestConfig(String configId, String defaultValue, long timeoutMills);
    String getConfigFromSys(String configId);
    void addConfigListener(String configId, ConfigurationChangeListener listener);
    void removeConfigListener(String configId, ConfigurationChangeListener listener);
    Set<ConfigurationChangeListener> getConfigListeners(String configId);
}
