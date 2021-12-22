package cn.heimdall.server;

import cn.heimdall.core.cluster.NodeInfo;
import cn.heimdall.core.cluster.NodeInfoManager;
import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.config.ConfigurationCache;
import cn.heimdall.core.config.ConfigurationFactory;
import cn.heimdall.core.utils.constants.ConfigurationKeys;
import cn.heimdall.core.network.coordinator.Coordinator;
import cn.heimdall.core.utils.common.ArgsUtils;
import cn.heimdall.core.utils.spi.EnhancedServiceLoader;

import java.util.Set;
import java.util.stream.Collectors;

public class Starter {
    public static void main(String[] args) {
        ArgsUtils.parseArgs(args);
        Configuration configuration = ConfigurationFactory.getInstance();
        //添加三个角色地址的监听器
        ConfigurationCache.addConfigListener(ConfigurationKeys.GUARDER_REAL_HOSTS);
        ConfigurationCache.addConfigListener(ConfigurationKeys.COMPUTE_HOSTS);
        ConfigurationCache.addConfigListener(ConfigurationKeys.STORAGE_HOSTS);

        //初始化节点信息
        NodeInfoManager nodeInfoManager = NodeInfoManager.getInstance();
        nodeInfoManager.init();
        final NodeInfo nodeInfo = nodeInfoManager.getNodeInfo();

        //获取角色对应的协调器对象
        Set<Coordinator> coordinators = nodeInfo.getNodeRoles().stream().map(nodeRole ->
                EnhancedServiceLoader.load(Coordinator.class, nodeRole.getName())).collect(Collectors.toSet());

        //初始化netty server 并启动
        NettyServer server = new NettyServer(coordinators, configuration);


        server.multiNettyServerStart();
    }
}
