package client;

import cn.heimdall.core.utils.enums.NodeRole;

import java.util.Arrays;
import java.util.List;

public class ClientInfo {
    private String appName;
    private String host;
    private List<NodeRole> nodeRoles = Arrays.asList(NodeRole.CLIENT);

    public String getAppName() {
        return appName;
    }

    public String getHost() {
        return host;
    }

    public ClientInfo setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public ClientInfo setHost(String host) {
        this.host = host;
        return this;
    }

    public List<NodeRole> getNodeRoles() {
        return nodeRoles;
    }
}
