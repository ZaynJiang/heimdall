package cn.heimdall.core.cluster;

import cn.heimdall.core.message.NodeRole;

import java.util.List;

public class NodeInfo {

    private List<NodeRole> nodeRoles;

    private boolean storage;

    private boolean guarder;

    private boolean compute;

    private String nodeName;

    private String host;

    //监控数据传输端口
    private int transportPort;

    //管理类数据端口
    private int managePort;

    private int httpPort;

    public List<NodeRole> getNodeRoles() {
        return nodeRoles;
    }

    public NodeInfo setNodeRoles(List<NodeRole> nodeRoles) {
        this.nodeRoles = nodeRoles;
        return this;
    }

    public String getNodeName() {
        return nodeName;
    }

    public NodeInfo setNodeName(String nodeName) {
        this.nodeName = nodeName;
        return this;
    }

    public String getHost() {
        return host;
    }

    public NodeInfo setHost(String host) {
        this.host = host;
        return this;
    }

    public int getTransportPort() {
        return transportPort;
    }

    public NodeInfo setTransportPort(int transportPort) {
        this.transportPort = transportPort;
        return this;
    }

    public int getManagePort() {
        return managePort;
    }

    public NodeInfo setManagePort(int managePort) {
        this.managePort = managePort;
        return this;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public NodeInfo setHttpPort(int httpPort) {
        this.httpPort = httpPort;
        return this;
    }

    public boolean isStorage() {
        return storage;
    }

    public NodeInfo setStorage(boolean storage) {
        this.storage = storage;
        return this;
    }

    public boolean isGuarder() {
        return guarder;
    }

    public NodeInfo setGuarder(boolean guarder) {
        this.guarder = guarder;
        return this;
    }

    public boolean isCompute() {
        return compute;
    }

    public NodeInfo setCompute(boolean compute) {
        this.compute = compute;
        return this;
    }
}

