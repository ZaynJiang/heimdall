package cn.heimdall.core.config;

import io.netty.util.NettyRuntime;

/**
 * 管理类配置
 */
public class NetworkManageConfig extends NetworkConfig{
  public final static int MANAGE_WORK_THREAD_SIZE = NettyRuntime.availableProcessors() * 2;
}
