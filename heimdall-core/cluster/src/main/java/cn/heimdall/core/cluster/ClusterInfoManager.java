package cn.heimdall.core.cluster;

import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.config.ConfigurationFactory;
import cn.heimdall.core.config.constants.ConfigurationKeys;
import cn.heimdall.core.message.NodeRole;
import cn.heimdall.core.message.body.heartbeat.NodeHeartbeatRequest;
import cn.heimdall.core.message.body.heartbeat.NodeHeartbeatResponse;
import cn.heimdall.core.message.body.register.NodeRegisterRequest;
import cn.heimdall.core.utils.common.CollectionUtil;
import cn.heimdall.core.utils.common.CurrentTimeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

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
                    nodeRegisterRequest.getIp(), nodeRegisterRequest.getHost());
            return;
        }
        long currentTime = CurrentTimeFactory.currentTimeMillis();
        nodeRoles.stream().forEach(nodeRole -> clusterInfo.putInetSocketAddress(nodeRole,
                new InetSocketAddress(nodeRegisterRequest.getIp(), nodeRegisterRequest.getHost()), currentTime));
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
        long currentTime = CurrentTimeFactory.currentTimeMillis();
        nodeRoles.stream().forEach(nodeRole -> clusterInfo.putInetSocketAddress(nodeRole,
                new InetSocketAddress(nodeHeartbeatRequest.getIp(), nodeHeartbeatRequest.getPort()), currentTime));
    }

    /**
     * 各个节点向集群发送心跳信息后更新本集群信息
     */
    public void doUpdateNodeInfo(NodeHeartbeatResponse nodeHeartbeatResponse) {
        Map<NodeRole, Map<InetSocketAddress, Long>> allAddresses = nodeHeartbeatResponse.getAddresses();
        if (allAddresses == null) {
            LOGGER.warn("doUpdateNodeInfo, addresses is null, ip:{}，port:{}",
                    nodeHeartbeatResponse.getHost(), nodeHeartbeatResponse.getPort());
            return;
        }
        allAddresses.forEach((nodeRole, addresses) ->
                addresses.forEach((address, timestamp) ->
                        clusterInfo.putInetSocketAddress(nodeRole, address, timestamp)));
    }


    /**
     * 获取集群信息
     */
    public ClusterInfo getClusterInfo() {
        return this.clusterInfo;
    }
}
