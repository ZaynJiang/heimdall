package cn.heimdall.core.config;

import java.util.Set;

public class LocalConfiguration extends AbstractConfiguration {

    @Override
    public boolean putConfigIfAbsent(String dataId, String content, long timeoutMills) {
        return false;
    }

    @Override
    public boolean putConfig(String dataId, String content, long timeoutMills) {
        return false;
    }

    @Override
    public boolean removeConfig(String dataId, long timeoutMills) {
        return false;
    }

    @Override
    public String getTypeName() {
        return null;
    }

    @Override
    public String getLatestConfig(String configId, String defaultValue, long timeoutMills) {
        return null;
    }

    @Override
    public void addConfigListener(String configId, ConfigurationChangeListener listener) {

    }

    @Override
    public void removeConfigListener(String configId, ConfigurationChangeListener listener) {

    }

    @Override
    public Set<ConfigurationChangeListener> getConfigListeners(String configId) {
        return null;
    }
}
