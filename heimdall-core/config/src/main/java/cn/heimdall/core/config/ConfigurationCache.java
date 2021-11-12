package cn.heimdall.core.config;

import cn.heimdall.core.utils.common.StringUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationCache extends ConfigurationChangeListener{

    private static final String METHOD_PREFIX = "get";

    private static final String METHOD_LATEST_CONFIG = METHOD_PREFIX + "LatestConfig";

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationCache.class);

    private static final ConcurrentHashMap<String, ObjectWrapper> CONFIG_CACHE = new ConcurrentHashMap<>();

    //获取配置对象的cache代理对象
    public Configuration getProxyConfiguration(Configuration originalConfiguration) {
        return (Configuration) Enhancer.create(Configuration.class,
                (MethodInterceptor)(proxy, method, args, methodProxy) -> {
                    if (method.getName().startsWith(METHOD_PREFIX)
                            && !method.getName().equalsIgnoreCase(METHOD_LATEST_CONFIG)) {
                        String rawConfigId = (String)args[0];
                        ObjectWrapper wrapper = CONFIG_CACHE.get(rawConfigId);
                        String type = method.getName().substring(METHOD_PREFIX.length());
                        if (!ObjectWrapper.supportType(type)) {
                            type = null;
                        }
                        if (null == wrapper) {
                            Object result = method.invoke(originalConfiguration, args);
                            if (result != null) {
                                wrapper = new ObjectWrapper(result, type);
                                CONFIG_CACHE.put(rawConfigId, wrapper);
                            }
                        }
                        //如果缓存有，就走缓存
                        return wrapper == null ? null : wrapper.convertData(type);
                    }
                    return method.invoke(originalConfiguration, args);
                });
    }


    //添加配置变化监听器
    public static void addConfigListener(String configId, ConfigurationChangeListener... listeners) {
        if (StringUtil.isBlank(configId)) {
            LOGGER.warn("addConfigListener, configId is null");
            return;
        }
        synchronized (ConfigurationCache.class) {
            //配置对象绑定configId->缓存监听器
            ConfigurationFactory.getInstance().addConfigListener(configId, getInstance());
            //绑定configId->传入参数监听器
            if (null != listeners && listeners.length > 0) {
                for (ConfigurationChangeListener listener : listeners) {
                     ConfigurationFactory.getInstance().addConfigListener(configId, listener);
                }
            }
        }
    }

    public static ConfigurationCache getInstance() {
        return ConfigurationCacheInstance.INSTANCE;
    }

    private static class ConfigurationCacheInstance {
        private static final ConfigurationCache INSTANCE = new ConfigurationCache();
    }

    public void clear() {
        CONFIG_CACHE.clear();
    }

    @Override
    public void onChangeEvent(ConfigurationChangeEvent event) {
        ObjectWrapper wrapper = CONFIG_CACHE.get(event.getConfigId());
        // The wrapper.data only exists in the cache when it is not null.
        if (StringUtil.isNotBlank(event.getNewValue())) {
            if (wrapper == null) {
                CONFIG_CACHE.put(event.getConfigId(), new ObjectWrapper(event.getNewValue(), null));
            } else {
                Object newValue = new ObjectWrapper(event.getNewValue(), null).convertData(wrapper.getType());
                if (!Objects.equals(wrapper.getData(), newValue)) {
                    CONFIG_CACHE.put(event.getConfigId(), new ObjectWrapper(newValue, wrapper.getType()));
                }
            }
        } else {
            CONFIG_CACHE.remove(event.getConfigId());
        }
    }

    private static class ObjectWrapper {

        static final String INT = "Int";
        static final String BOOLEAN = "Boolean";
        static final String LONG = "Long";
        static final String SHORT = "Short";

        private final Object data;
        private final String type;

        ObjectWrapper(Object data, String type) {
            this.data = data;
            this.type = type;
        }

        public Object getData() {
            return data;
        }

        public String getType() {
            return type;
        }

        public Object convertData(String aType) {
            if (data != null && Objects.equals(type, aType)) {
                return data;
            }
            if (data != null) {
                if (INT.equals(aType)) {
                    return Integer.parseInt(data.toString());
                } else if (BOOLEAN.equals(aType)) {
                    return Boolean.parseBoolean(data.toString());
                } else if (LONG.equals(aType)) {
                    return Long.parseLong(data.toString());
                } else if (SHORT.equals(aType)) {
                    return Short.parseShort(data.toString());
                }
                return String.valueOf(data);
            }
            return null;
        }

        public static boolean supportType(String type) {
            return INT.equalsIgnoreCase(type)
                    || BOOLEAN.equalsIgnoreCase(type)
                    || LONG.equalsIgnoreCase(type)
                    || SHORT.equalsIgnoreCase(type);
        }
    }
}
