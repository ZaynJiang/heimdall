package cn.heimdall.core.cluster;

import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.config.ConfigurationFactory;

public class ClusterInfoManager {
    private static volatile ClusterInfoManager INSTANCE;
    private static final Configuration CONFIG = ConfigurationFactory.getInstance();

    public ClusterInfoManager() {
        //todo 可以开启定时任务，定期清理过期clusterInfo
    }

    public static ClusterInfoManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ClusterInfoManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ClusterInfoManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 向集群注册集群信息
     */
    public void doRegisterNodeInfo() {

    }

    /**
     * TODO 改变节点信息,如何保持节点在线,记录最新的时间
     */
    public void doChangeNodeInfo() {

    }

    /**
     * 获取最新的集群信息
     */
    public void getLastClusterInfo (){

    }
}
