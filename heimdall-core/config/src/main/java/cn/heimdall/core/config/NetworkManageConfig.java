package cn.heimdall.core.config;

import io.netty.util.NettyRuntime;

import java.util.ArrayList;
import java.util.List;

public class NetworkManageConfig extends NetworkConfig{
    public static final int MANAGE_WORK_THREAD_SIZE = NettyRuntime.availableProcessors() * 2;

    public static final List<String> guarderIps;
    static {
        guarderIps = new ArrayList<>();
        //TODO 初始化守卫者ip。
        guarderIps.add("xxxxxx");
    }
}
