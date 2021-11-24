package cn.heimdall.core.config.file;

import cn.heimdall.core.config.AbstractConfiguration;
import cn.heimdall.core.config.ConfigFuture;
import cn.heimdall.core.config.ConfigurationChangeListener;
import cn.heimdall.core.config.constants.ConfigurationKeys;
import cn.heimdall.core.utils.common.CollectionUtil;
import cn.heimdall.core.utils.common.StringUtil;
import cn.heimdall.core.utils.thread.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FileConfiguration extends AbstractConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileConfiguration.class);

    private FileConfig fileConfig;

    private ExecutorService configOperateExecutor;

    private static final int CORE_CONFIG_OPERATE_THREAD = 1;

    private static final int MAX_CONFIG_OPERATE_THREAD = 2;

    private static final long LISTENER_CONFIG_INTERVAL = 1 * 1000;

    private static final String REGISTRY_TYPE = "file";

    public static final String SYS_FILE_RESOURCE_PREFIX = "file:";

    private final ConcurrentMap<String, Set<ConfigurationChangeListener>> configListenersMap = new ConcurrentHashMap<>(
            8);

    private final String targetFilePath;

    private volatile long targetFileLastModified;

    private final String name;

    private final boolean allowDynamicRefresh;


    public FileConfiguration() {
        this.name = null;
        this.targetFilePath = null;
        this.allowDynamicRefresh = false;
    }

    public FileConfiguration(String name) {
        this(name, true);
    }


    public FileConfiguration(String name, boolean allowDynamicRefresh) {
        LOGGER.info("The file name of the operation is {}", name);
        File file = getConfigFile(name);
        if (file == null) {
            targetFilePath = null;
        } else {
            targetFilePath = file.getPath();
            fileConfig = FileConfigFactory.load(file, name);
        }
        if (targetFilePath == null) {
            fileConfig = FileConfigFactory.load();
            this.allowDynamicRefresh = false;
        } else {
            //记录文件最后更新位置
            targetFileLastModified = new File(targetFilePath).lastModified();
            this.allowDynamicRefresh = allowDynamicRefresh;
        }
        this.name = name;
        configOperateExecutor = new ThreadPoolExecutor(CORE_CONFIG_OPERATE_THREAD, MAX_CONFIG_OPERATE_THREAD,
                Integer.MAX_VALUE, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
                new NamedThreadFactory("configOperate", MAX_CONFIG_OPERATE_THREAD));
    }

    private File getConfigFile(String name) {
        try {
            if (name == null) {
                throw new IllegalArgumentException("name can't be null");
            }
            boolean filePathCustom = name.startsWith(SYS_FILE_RESOURCE_PREFIX);
            String filePath = filePathCustom ? name.substring(SYS_FILE_RESOURCE_PREFIX.length()) : name;
            String decodedPath = URLDecoder.decode(filePath, StandardCharsets.UTF_8.name());
            File targetFile = getFileFromFileSystem(decodedPath);
            if (targetFile != null) {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("The configuration file used is {}", targetFile.getPath());
                }
                return targetFile;
            }

            if (!filePathCustom) {
                File classpathFile = getFileFromClasspath(name);
                if (classpathFile != null) {
                    return classpathFile;
                }
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("decode name error: {}", e.getMessage(), e);
        }

        return null;
    }

    private File getFileFromFileSystem(String decodedPath) {

        // run with jar file and not package third lib into jar file, this.getClass().getClassLoader() will be null
        URL resourceUrl = this.getClass().getClassLoader().getResource("");
        String[] tryPaths = null;
        if (resourceUrl != null) {
            tryPaths = new String[]{
                // first: project dir
                resourceUrl.getPath() + decodedPath,
                // second: system path
                decodedPath
            };
        } else {
            tryPaths = new String[]{
                decodedPath
            };
        }

        for (String tryPath : tryPaths) {
            File targetFile = new File(tryPath);
            if (targetFile.exists()) {
                return targetFile;
            }

            // try to append config suffix
            for (String s : FileConfigFactory.getSuffixSet()) {
                targetFile = new File(tryPath + ConfigurationKeys.FILE_CONFIG_SPLIT_CHAR + s);
                if (targetFile.exists()) {
                    return targetFile;
                }
            }
        }

        return null;
    }

    private File getFileFromClasspath(String name) throws UnsupportedEncodingException {
        URL resource = this.getClass().getClassLoader().getResource(name);
        if (resource == null) {
            for (String s : FileConfigFactory.getSuffixSet()) {
                resource = this.getClass().getClassLoader().getResource(name + ConfigurationKeys.FILE_CONFIG_SPLIT_CHAR + s);
                if (resource != null) {
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("The configuration file used is {}", resource.getPath());
                    }
                    String path = resource.getPath();
                    path = URLDecoder.decode(path, StandardCharsets.UTF_8.name());
                    return new File(path);
                }
            }
        } else {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("The configuration file used is {}", name);
            }
            String path = resource.getPath();
            path = URLDecoder.decode(path, StandardCharsets.UTF_8.name());
            return new File(path);
        }
        return null;
    }

    @Override
    public String getLatestConfig(String configId, String defaultValue, long timeoutMills) {
        //优先取系统中的变量
        String value = getConfigFromSys(configId);
        if (value != null) {
            return value;
        }
        ConfigFuture configFuture = new ConfigFuture(configId, defaultValue, ConfigFuture.ConfigOperation.GET, timeoutMills);
        configOperateExecutor.submit(new ConfigOperateRunnable(configFuture));
        Object getValue = configFuture.get();
        return getValue == null ? null : String.valueOf(getValue);
    }

    @Override
    public boolean putConfig(String configId, String content, long timeoutMills) {
        ConfigFuture configFuture = new ConfigFuture(configId, content, ConfigFuture.ConfigOperation.PUT, timeoutMills);
        configOperateExecutor.submit(new ConfigOperateRunnable(configFuture));
        return (Boolean) configFuture.get();
    }

    @Override
    public boolean putConfigIfAbsent(String configId, String content, long timeoutMills) {
        ConfigFuture configFuture = new ConfigFuture(configId, content, ConfigFuture.ConfigOperation.PUTIFABSENT, timeoutMills);
        configOperateExecutor.submit(new ConfigOperateRunnable(configFuture));
        return (Boolean) configFuture.get();
    }

    @Override
    public boolean removeConfig(String configId, long timeoutMills) {
        ConfigFuture configFuture = new ConfigFuture(configId, null, ConfigFuture.ConfigOperation.REMOVE, timeoutMills);
        configOperateExecutor.submit(new ConfigOperateRunnable(configFuture));
        return (Boolean) configFuture.get();
    }

    @Override
    public void addConfigListener(String configId, ConfigurationChangeListener listener) {
        if (StringUtil.isBlank(configId) || listener == null) {
            return;
        }
        Set<ConfigurationChangeListener> listeners = configListenersMap.computeIfAbsent(configId, key -> new HashSet<>());
        if (listeners.contains(listener)) {
            return;
        }
        listeners.add(listener);
    }

    @Override
    public void removeConfigListener(String configId, ConfigurationChangeListener listener) {
        if (StringUtil.isBlank(configId) || listener == null) {
            return;
        }
        Set<ConfigurationChangeListener> configListeners = getConfigListeners(configId);
        if (!CollectionUtil.isEmpty(configListeners)) {
            configListeners.remove(listener);
            if (configListeners.isEmpty()) {
                configListenersMap.remove(configId);
            }
        }
        listener.onShutDown();
    }

    @Override
    public Set<ConfigurationChangeListener> getConfigListeners(String configId) {
        return configListenersMap.get(configId);
    }

    @Override
    public String getTypeName() {
        return REGISTRY_TYPE;
    }


    class ConfigOperateRunnable implements Runnable {

        private ConfigFuture configFuture;

        public ConfigOperateRunnable(ConfigFuture configFuture) {
            this.configFuture = configFuture;
        }

        @Override
        public void run() {
            if (configFuture != null) {
                if (configFuture.isTimeout()) {
                    setFailResult(configFuture);
                    return;
                }
                try {
                    if (allowDynamicRefresh) {
                        long tempLastModified = new File(targetFilePath).lastModified();
                        if (tempLastModified > targetFileLastModified) {
                            FileConfig tempConfig = FileConfigFactory.load(new File(targetFilePath), name);
                            if (tempConfig != null) {
                                fileConfig = tempConfig;
                                targetFileLastModified = tempLastModified;
                            }
                        }
                    }
                    if (configFuture.getOperation() == ConfigFuture.ConfigOperation.GET) {
                        String result = fileConfig.getString(configFuture.getDataId());
                        configFuture.setResult(result);
                    } else if (configFuture.getOperation() == ConfigFuture.ConfigOperation.PUT) {
                        //todo
                        configFuture.setResult(Boolean.TRUE);
                    } else if (configFuture.getOperation() == ConfigFuture.ConfigOperation.PUTIFABSENT) {
                        //todo
                        configFuture.setResult(Boolean.TRUE);
                    } else if (configFuture.getOperation() == ConfigFuture.ConfigOperation.REMOVE) {
                        //todo
                        configFuture.setResult(Boolean.TRUE);
                    }
                } catch (Exception e) {
                    setFailResult(configFuture);
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Could not found property {}, try to use default value instead. exception:{}",
                                configFuture.getDataId(), e.getMessage());
                    }
                }
            }
        }

        private void setFailResult(ConfigFuture configFuture) {
            if (configFuture.getOperation() == ConfigFuture.ConfigOperation.GET) {
                String result = configFuture.getContent();
                configFuture.setResult(result);
            } else {
                configFuture.setResult(Boolean.FALSE);
            }
        }

    }

}
