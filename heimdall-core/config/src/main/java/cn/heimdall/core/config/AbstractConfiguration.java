package cn.heimdall.core.config;

public abstract class AbstractConfiguration implements Configuration {

    protected static final long DEFAULT_CONFIG_TIMEOUT = 5 * 1000;

    @Override
    public short getShort(String dataId, short defaultValue) {
        String result = getConfig(dataId, String.valueOf(defaultValue));
        return Short.parseShort(result);
    }

    @Override
    public int getInt(String dataId, int defaultValue) {
        String result = getConfig(dataId, String.valueOf(defaultValue));
        return Integer.parseInt(result);
    }

    @Override
    public long getLong(String dataId, long defaultValue) {
        String result = getConfig(dataId, String.valueOf(defaultValue));
        return Long.parseLong(result);
    }

    @Override
    public boolean getBoolean(String dataId, boolean defaultValue) {
        String result = getConfig(dataId, String.valueOf(defaultValue));
        return Boolean.parseBoolean(result);
    }

    @Override
    public String getConfig(String dataId, String defaultValue) {
        return getConfig(dataId, String.valueOf(defaultValue));
    }

    @Override
    public boolean putConfigIfAbsent(String dataId, String content) {
        return putConfigIfAbsent(dataId, content, DEFAULT_CONFIG_TIMEOUT);
    }

    @Override
    public String getConfigFromSys(String dataId) {
        return System.getProperty(dataId);
    }

    public abstract boolean putConfigIfAbsent(String dataId, String content, long timeoutMills);

    public abstract  boolean putConfig(String dataId, String content, long timeoutMills);

    public abstract boolean removeConfig(String dataId, long timeoutMills);

    public abstract String getTypeName();
}
