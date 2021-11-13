package cn.heimdall.core.cluster;

import java.net.InetSocketAddress;
import java.util.List;

public class ClusterInfo {
    private String clusterName;
    private int nodeSize;
    private int guarderSize;
    private int computeSize;
    private int storageSize;
    // todo 更新不同角色地
    private List<InetSocketAddress> guarderAddress;
    private List<InetSocketAddress> computeAddress;
    private List<InetSocketAddress> storageAddress;
}
