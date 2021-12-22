package cn.heimdall.core.config;

import cn.heimdall.core.utils.constants.ConfigurationKeys;

public class NetworkActionConfig extends NetworkConfig{
    public int getMinServerPoolSize() {
        return 50;
    }

    public int getMaxServerPoolSize() {
        return 500;
    }

    public int getKeepAliveTime() {
        return 500;
    }

    public int getServerShutdownWaitTime() {
        return CONFIG.getInt(ConfigurationKeys.SHUTDOWN_WAIT, 3);
    }

    public int getMaxTaskQueueSize() {
        return 20000;
    }

    public int getPort() {
        return CONFIG.getInt(ConfigurationKeys.HTTP_PORT, 7400);
    }
}
