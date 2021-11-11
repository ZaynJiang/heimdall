package cn.heimdall.core.config;

import cn.heimdall.core.config.constants.ConfigurationKeys;
import cn.heimdall.core.config.file.FileConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationFactory.class);
    // 服务端默认从heimdall.yml中读取
    private static final String SERVER_CONF_DEFAULT = "heimdall.yml";

    private static volatile Configuration instance = null;

    //只是加载当前配置的文件对象。
    public static Configuration CURRENT_FILE_INSTANCE;

    static {
        String heimdallConfigName = System.getProperty(ConfigurationKeys.SYSTEM_PROPERTY_HEIMDALL_CONFIG_NAME,
                ConfigurationKeys.SERVER_CONF_DEFAULT);
        //TODO,可以设置不同环境的文件读取
        Configuration configuration =  new FileConfiguration(heimdallConfigName, false);
        //TODO, 这里执行获取代理对象，如客户端可以获取spring的配置放进去
        Configuration extConfiguration = null;
        CURRENT_FILE_INSTANCE = extConfiguration == null ? configuration : extConfiguration;
    }


    public static Configuration getInstance() {
        if (instance == null) {
            synchronized (Configuration.class) {
                if (instance == null) {
                    instance = buildConfiguration();
                }
            }
        }
        return instance;
    }

    private static Configuration buildConfiguration() {
        //TODO 可以做很多事情，比如可以从配置中心读取远程的配置对象
         //获取文件对象的缓存代理对象
         return ConfigurationCache.getInstance().getProxyConfiguration(CURRENT_FILE_INSTANCE);
    }

}
