package cn.heimdall.core.config;

/**
 * 传输类配置
 */
public class NetworkTransportConfig extends NetworkConfig{
    public int getMinServerPoolSize() {
        return 0;
    }

    public int getMaxServerPoolSize() {
        return 0;
    }

    public long getKeepAliveTime() {
        return 0;
    }

    public int getMaxTaskQueueSize() {
        return 10000;
    }
}
