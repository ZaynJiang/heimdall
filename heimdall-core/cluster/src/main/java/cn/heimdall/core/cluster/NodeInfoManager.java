package cn.heimdall.core.cluster;

import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.config.ConfigurationFactory;
import cn.heimdall.core.utils.common.NetUtil;
import cn.heimdall.core.utils.constants.ConfigurationKeys;
import cn.heimdall.core.utils.enums.NodeRole;
import cn.heimdall.core.utils.exception.ParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class NodeInfoManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterInfoManager.class);

    private static volatile NodeInfoManager INSTANCE;
    private static final Configuration CONFIG = ConfigurationFactory.getInstance();
    private final NodeInfo nodeInfo;

    public NodeInfoManager() {
        this.nodeInfo = new NodeInfo();
    }

    public static NodeInfoManager getInstance() {
        if (INSTANCE == null) {
            synchronized (NodeInfoManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NodeInfoManager();
                }
            }
        }
        return INSTANCE;
    }

    public void init() {
        nodeInfo.setHost(NetUtil.getLocalIp())
                //todo 默认值如何放置
                .setManagePort(CONFIG.getInt(ConfigurationKeys.MANAGE_PORT, 7200))
                .setTransportPort(CONFIG.getInt(ConfigurationKeys.TRANSPORT_PORT, 7300))
                .setHttpPort(CONFIG.getInt(ConfigurationKeys.HTTP_PORT, 7400))
                .setNodeName(CONFIG.getConfig(ConfigurationKeys.NODE_NAME, "my-node"));

        List<NodeRole> nodeRoles = new ArrayList<>();
        if (ConfigurationFactory.getInstance().getBoolean(ConfigurationKeys.NODE_STORAGE, true)) {
            nodeRoles.add(NodeRole.STORAGE);
            nodeInfo.setStorage(true);
        }
        if (ConfigurationFactory.getInstance().getBoolean(ConfigurationKeys.NODE_COMPUTE, true)) {
            nodeRoles.add(NodeRole.COMPUTE);
            nodeInfo.setCompute(true);
        }
        if (ConfigurationFactory.getInstance().getBoolean(ConfigurationKeys.NODE_GUARDER, true)) {
            nodeRoles.add(NodeRole.GUARDER);
            nodeInfo.setGuarder(true);
        }
        if (ConfigurationFactory.getInstance().getBoolean(ConfigurationKeys.NODE_ACTION, true)) {
            nodeRoles.add(NodeRole.ACTION);
            nodeInfo.setAction(true);
        }
        if (nodeRoles.size() == 0) {
            throw new ParamException("node roles is null");
        }
        nodeInfo.setNodeRoles(nodeRoles);
    }

    public NodeInfo getNodeInfo() {
        return nodeInfo;
    }
}
