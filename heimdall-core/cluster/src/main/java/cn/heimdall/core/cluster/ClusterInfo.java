package cn.heimdall.core.cluster;

import cn.heimdall.core.message.NodeRole;
import cn.heimdall.core.utils.common.CurrentTimeFactory;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 整体的集群信息
 */
public class ClusterInfo {
    private String clusterName;
    // 地址-> 最新更新时间
    private Map<InetSocketAddress, Long> guarderNodes;
    private Map<InetSocketAddress, Long> computeNodes;
    private Map<InetSocketAddress, Long> storageNodes;

    public ClusterInfo() {
    }

    public ClusterInfo(String clusterName) {
        this.clusterName = clusterName;
        guarderNodes = new ConcurrentHashMap<>();
        computeNodes = new ConcurrentHashMap<>();
        storageNodes = new ConcurrentHashMap<>();
    }

    //添加节点角色的地址信息到全局集群信息中
    public void putInetSocketAddress(NodeRole nodeRole, InetSocketAddress inetSocketAddress) {
        switch (nodeRole) {
            case COMPUTE:
                computeNodes.put(inetSocketAddress, CurrentTimeFactory.currentTimeMillis());
                break;
            case GUARDER:
                guarderNodes.put(inetSocketAddress, CurrentTimeFactory.currentTimeMillis());
                break;
            case STORAGE:
                storageNodes.put(inetSocketAddress, CurrentTimeFactory.currentTimeMillis());
                break;
        }
    }

    public Set<InetSocketAddress> getActiveInetSocketAddress(NodeRole nodeRole, long expireThreshold){
        long currentTimestamp = CurrentTimeFactory.currentTimeMillis();
        switch (nodeRole) {
            case COMPUTE:
                return computeNodes.keySet().stream().filter(key -> currentTimestamp - computeNodes.get(key) < expireThreshold).
                        collect(Collectors.toSet());
            case GUARDER:
                return guarderNodes.keySet().stream().filter(key -> currentTimestamp - guarderNodes.get(key) < expireThreshold).
                        collect(Collectors.toSet());
            case STORAGE:
                return storageNodes.keySet().stream().filter(key -> currentTimestamp - storageNodes.get(key) < expireThreshold).
                        collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }


    public String getClusterName() {
        return clusterName;
    }

    public Map<InetSocketAddress, Long> getGuarderNodes() {
        return guarderNodes;
    }

    public Map<InetSocketAddress, Long> getComputeNodes() {
        return computeNodes;
    }

    public Map<InetSocketAddress, Long> getStorageNodes() {
        return storageNodes;
    }
}
