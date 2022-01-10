package cn.heimdall.core.utils.constants;

/**
 *
 */
public interface ConfigurationKeys {

    String IP_PORT_SPLIT_CHAR = ":";

    String SYSTEM_PROPERTY_HEIMDALL_CONFIG_TARGET = "heimdall.config.target";
    String SYSTEM_PROPERTY_HEIMDALL_CONFIG_NAME = "heimdall.config.name";
    String SERVER_CONF_DEFAULT = "heimdall.yml";

    String CLIENT_APP_NAME = "client.app.name";


    String FILE_CONFIG_SPLIT_CHAR = ".";

    //集群
    String CLUSTER_PREFIX = "cluster.";
    String CLUSTER_NAME = CLUSTER_PREFIX + "name";

    String TRANSPORT_PREFIX = "transport.";


    //查询类端口
    String HTTP_PORT = "http.port";
    //监控数据传输端口
    String TRANSPORT_PORT = TRANSPORT_PREFIX + "port";
    //管理类端口
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


    //停止等待
    String SHUTDOWN_WAIT = TRANSPORT_PREFIX + "shutdown.wait";


    String METRIC_UPLOADER_PERIOD = "metric.uploader.period";
}
