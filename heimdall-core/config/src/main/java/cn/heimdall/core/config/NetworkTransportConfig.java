package cn.heimdall.core.config;

import cn.heimdall.core.utils.constants.ConfigurationKeys;

/**
 * 传输类配置
 */
public class NetworkTransportConfig extends NetworkConfig{
    public int getMinServerPoolSize() {
        return 50;
    }

    public int getMaxServerPoolSize() {
        return 500;
    }

    public long getKeepAliveTime() {
        return 500;
    }

    public int getMaxTaskQueueSize() {
        return 10000;
    }

    public int getPort() {
        return CONFIG.getInt(ConfigurationKeys.TRANSPORT_PORT, 7300);
    }
}
