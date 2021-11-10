package cn.heimdall.core.config.file;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class YamlFileConfig implements FileConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(YamlFileConfig.class);
    private Map configMap;

    public YamlFileConfig(File file, String name) {
        Yaml yaml = new Yaml();
        try {
            configMap = (Map) yaml.load(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("file not found");
        }
    }

    @Override
    public String getString(String path) {
        try {
            Map config = configMap;
            String[] configId = path.split("\\.");
            for (int i = 0; i < configId.length - 1; i++) {
                if (config.containsKey(configId[i])) {
                    config = (Map) config.get(configId[i]);
                } else {
                    return null;
                }
            }
            Object value = config.get(configId[configId.length - 1]);
            return value == null ? null : String.valueOf(value);
        } catch (Exception e) {
            LOGGER.warn("get config data error" + path, e);
            return null;
        }
    }
}
