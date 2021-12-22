package cn.heimdall.core.config;

import cn.heimdall.core.utils.constants.ConfigurationKeys;
import io.netty.util.NettyRuntime;

/**
 * 管理类配置
 */
public class NetworkManageConfig extends NetworkConfig {
    public final static int MANAGE_WORK_THREAD_SIZE = NettyRuntime.availableProcessors() * 2;

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

    public int getPort(){
        return CONFIG.getInt(ConfigurationKeys.MANAGE_PORT, 7200);

    }
}
