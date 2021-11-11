package cn.heimdall.server;

import cn.heimdall.core.config.ConfigurationCache;
import cn.heimdall.core.config.ConfigurationFactory;
import static cn.heimdall.core.config.constants.ConfigurationKeys.CLUSTER_NAME;

public class Starter {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(ConfigurationFactory.getInstance().getConfig(CLUSTER_NAME, ""));
        System.out.println(ConfigurationFactory.getInstance().getConfig(CLUSTER_NAME, ""));
        ConfigurationCache.addConfigListener(CLUSTER_NAME);
        Thread.sleep(100000);
    }
}
