package cn.heimdall.core.config;

public class HeimdallConfig {
    public static String SPI_CLASSLOADER = "cn.heimdall.core.spi.classloader";
    public static String ENDPOINT_BEGIN_CHAR = "/";
    public static String IP_PORT_SPLIT_CHAR = ":";
    protected static final Configuration CONFIG = ConfigurationFactory.getInstance();
    public static Long NODE_HEART_BEAT_EXPIRE_TIME = 5 * 3000L;
}
