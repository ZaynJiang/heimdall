package cn.heimdall.core.cluster;

import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.config.ConfigurationFactory;
import cn.heimdall.core.config.constants.ConfigurationKeys;
import cn.heimdall.core.message.NodeRole;
import cn.heimdall.core.message.body.hearbeat.NodeHeartbeatRequest;
import cn.heimdall.core.message.body.register.NodeRegisterRequest;
import cn.heimdall.core.utils.common.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

public class ClusterInfoManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterInfoManager.class);

    private static volatile ClusterInfoManager INSTANCE;
    private static final Configuration CONFIG = ConfigurationFactory.getInstance();

    private final ClusterInfo clusterInfo;

    public ClusterInfoManager() {
        //todo 可以开启定时任务，定期清理过期clusterInfo
        clusterInfo = new ClusterInfo(CONFIG.getConfig(ConfigurationKeys.CLUSTER_NAME, "heimdall"));
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
     * 各个节点向集群注册集群信息
     */
    public void doRegisterNodeInfo(NodeRegisterRequest nodeRegisterRequest) {
        List<NodeRole> nodeRoles = nodeRegisterRequest.getNodeRoles();
        if (!CollectionUtil.isEmpty(nodeRoles)) {
            LOGGER.warn("doRegisterNodeInfo, nodeRoles is null, ip:{}，port:{}",
                    nodeRegisterRequest.getIp(), nodeRegisterRequest.getPort());
            return;
        }
        nodeRoles.stream().forEach(nodeRole -> clusterInfo.putInetSocketAddress(nodeRole,
                new InetSocketAddress(nodeRegisterRequest.getIp(), nodeRegisterRequest.getPort())));
    }

    /**
     * 各个节点向集群发送心跳信息并更新
     */
    public void doUpdateNodeInfo(NodeHeartbeatRequest nodeHeartbeatRequest) {
        List<NodeRole> nodeRoles = nodeHeartbeatRequest.getNodeRoles();
        if (!CollectionUtil.isEmpty(nodeRoles)) {
            LOGGER.warn("doUpdateNodeInfo, nodeRoles is null, ip:{}，port:{}",
                    nodeHeartbeatRequest.getIp(), nodeHeartbeatRequest.getPort());
            return;
        }
        nodeRoles.stream().forEach(nodeRole -> clusterInfo.putInetSocketAddress(nodeRole,
                new InetSocketAddress(nodeHeartbeatRequest.getIp(), nodeHeartbeatRequest.getPort())));
    }


    /**
     * 获取最新的集群信息
     */
    public ClusterInfo getLastClusterInfo (){
        return this.clusterInfo;
    }
}
