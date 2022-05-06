package cn.heimdall.client.agent.core.conf;

import cn.heimdall.client.agent.core.boot.AgentJarPath;
import cn.heimdall.client.agent.core.exception.AgentJarNotFoundException;
import cn.heimdall.client.agent.core.exception.ConfigNotFoundException;
import cn.heimdall.core.utils.common.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class AgentConfigInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(AgentConfigInitializer.class);
    private static final String SPECIFIED_CONFIG_PATH = "heimdall_config";
    private static final String DEFAULT_CONFIG_FILE_NAME = "/config/agent.config";
    private static final String ENV_KEY_PREFIX = "heimdall.";
    private static Properties AGENT_SETTINGS;
    private static boolean IS_INIT_COMPLETED = false;

    public static void initializeAgentConfig(String agentOptions) {
        AGENT_SETTINGS = new Properties();
        try (final InputStreamReader configFileStream = loadConfig()) {
            AGENT_SETTINGS.load(configFileStream);
            for (String key : AGENT_SETTINGS.stringPropertyNames()) {
                String value = (String) AGENT_SETTINGS.get(key);
                AGENT_SETTINGS.put(key, value);
            }
        } catch (Exception e) {
            LOGGER.error("initializeAgentConfig error", e);
        }

        IS_INIT_COMPLETED = true;
    }

    public static void initializeConfig(Class configClass) {
        if (AGENT_SETTINGS == null) {
            LOGGER.error("Plugin configs have to be initialized after core config initialization.");
            return;
        }
/*        try {
            //AgentConfigInitializer.initialize(AGENT_SETTINGS, configClass);
        } catch (IllegalAccessException e) {
            LOGGER.error("Failed to set the agent settings {} to Config={}",
                    AGENT_SETTINGS, configClass, e);
        }*/
    }

    private static void overrideConfigByAgentOptions(String agentOptions) throws IllegalAccessException {
        for (List<String> terms : parseAgentOptions(agentOptions)) {
            if (terms.size() != 2) {
                throw new IllegalArgumentException("[" + terms + "] is not a key-value pair.");
            }
            AGENT_SETTINGS.put(terms.get(0), terms.get(1));
        }
    }

    private static List<List<String>> parseAgentOptions(String agentOptions) {
        List<List<String>> options = new ArrayList<>();
        List<String> terms = new ArrayList<>();
        boolean isInQuotes = false;
        StringBuilder currentTerm = new StringBuilder();
        for (char c : agentOptions.toCharArray()) {
            if (c == '\'' || c == '"') {
                isInQuotes = !isInQuotes;
            } else if (c == '=' && !isInQuotes) {   // key-value pair uses '=' as separator
                terms.add(currentTerm.toString());
                currentTerm = new StringBuilder();
            } else if (c == ',' && !isInQuotes) {   // multiple options use ',' as separator
                terms.add(currentTerm.toString());
                currentTerm = new StringBuilder();

                options.add(terms);
                terms = new ArrayList<>();
            } else {
                currentTerm.append(c);
            }
        }
        // add the last term and option without separator
        terms.add(currentTerm.toString());
        options.add(terms);
        return options;
    }

    public static boolean isInitCompleted() {
        return IS_INIT_COMPLETED;
    }

    private static void overrideConfigBySystemProp() throws IllegalAccessException {
        Properties systemProperties = System.getProperties();
        for (final Map.Entry<Object, Object> prop : systemProperties.entrySet()) {
            String key = prop.getKey().toString();
            if (key.startsWith(ENV_KEY_PREFIX)) {
                String realKey = key.substring(ENV_KEY_PREFIX.length());
                AGENT_SETTINGS.put(realKey, prop.getValue());
            }
        }
    }

    private static InputStreamReader loadConfig() throws ConfigNotFoundException, AgentJarNotFoundException {
        String specifiedConfigPath = System.getProperty(SPECIFIED_CONFIG_PATH);
        File configFile = StringUtil.isEmpty(specifiedConfigPath) ? new File(
                AgentJarPath.getPath(), DEFAULT_CONFIG_FILE_NAME) : new File(specifiedConfigPath);

        if (configFile.exists() && configFile.isFile()) {
            try {
                LOGGER.debug("Config file found in " + configFile);

                return new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8);
            } catch (FileNotFoundException e) {
                throw new ConfigNotFoundException("Failed to load agent.config", e);
            }
        }
        throw new ConfigNotFoundException("Failed to load agent.config.");
    }
}
