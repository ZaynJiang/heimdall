package cn.heimdall.core.config.constants;

/**
 *
 */
public interface ConfigurationKeys {

    String SYSTEM_PROPERTY_HEIMDALL_CONFIG_NAME = "heimdall.config.name";
    String SERVER_CONF_DEFAULT = "heimdall.yml";

    String FILE_CONFIG_SPLIT_CHAR = ".";

    //集群
    String CLUSTER_PREFIX = "cluster.";
    String CLUSTER_NAME = CLUSTER_PREFIX + "name";

    //网络端口
    String HTTP_PORT = "http.port";
    String TRANSPORT_PORT = "transport.port";
    String MANAGE_PORT = "manage.port";

    //节点
    String NODE_PREFIX = "node.";
    String NODE_NAME = NODE_PREFIX + "name";
    String NODE_GUARDER = NODE_PREFIX + "guarder";
    String NODE_COMPUTE = NODE_PREFIX + "compute";
    String NODE_STORAGE = NODE_PREFIX + "storage";

    // guarder
    String GUARDER_PREFIX = "guarder.";
    String GUARDER_HOSTS = "hosts";
    //seek hosts
    String GUARDER_SEED_HOSTS = "guarder.seek.hosts";
    String GUARDER_REAL_HOSTS = GUARDER_PREFIX + "real.hosts";

    // compute
    String COMPUTE_PREFIX = "compute.";
    String COMPUTE_HOSTS = COMPUTE_PREFIX + "hosts";

    // storage
    String STORAGE_PREFIX = "storage.";
    String STORAGE_HOSTS = STORAGE_PREFIX + "hosts";
}
