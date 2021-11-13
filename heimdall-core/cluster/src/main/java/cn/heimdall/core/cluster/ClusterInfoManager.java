package cn.heimdall.core.config.cluster;

import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.config.ConfigurationFactory;

public class ClusterInfoManager {
    private static volatile ClusterInfoManager INSTANCE;
    private static final Configuration CONFIG = ConfigurationFactory.getInstance();

    public static ClusterInfoManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ClusterInfoManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ClusterInfoManager();
                }
            }
        }
    }

    private static void registerNodeInfo() {

    }
}
