package cn.heimdall.core.config;

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
}
