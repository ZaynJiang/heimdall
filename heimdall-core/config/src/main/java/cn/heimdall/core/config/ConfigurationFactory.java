package cn.heimdall.core.config;

import cn.heimdall.core.config.constants.ConfigConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationFactory.class);

    private static final String SERVER_CONF_DEFAULT = "heimdall.conf";

    private static volatile Configuration instance = null;

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
        //TODO
        return null;
    }


    static {

        String heimdallConfigName = System.getProperty(ConfigConstants.SYSTEM_PROPERTY_HEIMDALL_CONFIG_NAME);
        if (heimdallConfigName == null) {
            heimdallConfigName = ConfigConstants.SERVER_CONF_DEFAULT;
        }
        //TODO
    }

}
