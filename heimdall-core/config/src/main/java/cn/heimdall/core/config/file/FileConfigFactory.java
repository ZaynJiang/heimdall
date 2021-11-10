package cn.heimdall.core.config.file;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Set;

public class FileConfigFactory {

    public static final String DEFAULT_TYPE = "YAML";

    private static final LinkedHashMap<String, String> SUFFIX_MAP = new LinkedHashMap<String, String>(1) {
        {
            put("yml", DEFAULT_TYPE);
        }
    };

    private static FileConfig loadDefaultConfig(File file, String name) {
        return new YamlFileConfig(file, name);
    }

    public static Set<String> getSuffixSet() {
        return SUFFIX_MAP.keySet();
    }

    public synchronized static void register(String suffix, String beanActiveName) {
        SUFFIX_MAP.put(suffix, beanActiveName);
    }

    public static FileConfig load() {
        return loadDefaultConfig(null,  null);
    }

    public static FileConfig load(File file, String name) {
        //todo 这里加载默认配置, 可以根据不同的后缀读取不同的配置文件
        return loadDefaultConfig(file, name);
    }
}
