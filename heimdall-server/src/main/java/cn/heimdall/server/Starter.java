package cn.heimdall.server;

import cn.heimdall.core.config.ConfigurationCache;
import cn.heimdall.core.config.ConfigurationFactory;
import cn.heimdall.core.config.constants.ConfigurationKeys;

public class Starter {
    public static void main(String[] args) throws InterruptedException {
        //添加三个角色地址的监听器
        ConfigurationCache.addConfigListener(ConfigurationKeys.GUARDER_REAL_HOSTS);
        ConfigurationCache.addConfigListener(ConfigurationKeys.COMPUTE_HOSTS);
        ConfigurationCache.addConfigListener(ConfigurationKeys.STORAGE_HOSTS);
        boolean guarderRole = ConfigurationFactory.getInstance().getBoolean(ConfigurationKeys.NODE_GUARDER, true);
        boolean computeRole = ConfigurationFactory.getInstance().getBoolean(ConfigurationKeys.NODE_COMPUTE, true);
        boolean storageRole = ConfigurationFactory.getInstance().getBoolean(ConfigurationKeys.NODE_STORAGE, true);
        //启动server
    }
}
