package cn.heimdall.server;

import cn.heimdall.core.cluster.NodeInfo;
import cn.heimdall.core.cluster.NodeInfoManager;
import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.config.ConfigurationCache;
import cn.heimdall.core.config.ConfigurationFactory;
import cn.heimdall.core.config.constants.ConfigurationKeys;

public class Starter {
    public static void main(String[] args) {
        Configuration configuration = ConfigurationFactory.getInstance();
        //添加三个角色地址的监听器
        ConfigurationCache.addConfigListener(ConfigurationKeys.GUARDER_REAL_HOSTS);
        ConfigurationCache.addConfigListener(ConfigurationKeys.COMPUTE_HOSTS);
        ConfigurationCache.addConfigListener(ConfigurationKeys.STORAGE_HOSTS);

        //初始化节点信息
        NodeInfoManager nodeInfoManager = NodeInfoManager.getInstance();
        nodeInfoManager.init();
        final NodeInfo nodeInfo = nodeInfoManager.getNodeInfo();

        //初始化netty server 并启动
        MultiNettyServer server = new MultiNettyServer(nodeInfo, configuration);
        server.multiNettyServerStart();
    }
}
