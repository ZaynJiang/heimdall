package cn.heimdall.core.config.file;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Set;

public class FileConfigFactory {

    public static final String DEFAULT_TYPE = "CONF";

    private static final LinkedHashMap<String, String> SUFFIX_MAP = new LinkedHashMap<String, String>(1) {
        {
            put("conf", DEFAULT_TYPE);
        }
    };


    public static SimpleFileConfig load() {
        return loadService(DEFAULT_TYPE, null, null);
    }

    public static SimpleFileConfig load(File targetFile, String name) {
        String fileName = targetFile.getName();
        String configType = getConfigType(fileName);
        return loadService(configType, new Class[]{File.class, String.class}, new Object[]{targetFile, name});
    }

    private static String getConfigType(String fileName) {
        String configType = DEFAULT_TYPE;
        int suffixIndex = fileName.lastIndexOf(".");
        if (suffixIndex > 0) {
            configType = SUFFIX_MAP.getOrDefault(fileName.substring(suffixIndex + 1), DEFAULT_TYPE);
        }

        return configType;
    }

    private static SimpleFileConfig loadService(String name, Class[] argsType, Object[] args) {
        SimpleFileConfig fileConfig = EnhancedServiceLoader.load(SimpleFileConfig.class, name, argsType, args);
        return fileConfig;
    }

    public static Set<String> getSuffixSet() {
        return SUFFIX_MAP.keySet();
    }

    public synchronized static void register(String suffix, String beanActiveName) {
        SUFFIX_MAP.put(suffix, beanActiveName);
    }


}
