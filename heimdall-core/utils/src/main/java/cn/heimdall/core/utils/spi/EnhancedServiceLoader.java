package cn.heimdall.core.utils.spi;


import cn.heimdall.core.utils.annotation.LoadLevel;
import cn.heimdall.core.utils.common.CollectionUtil;
import cn.heimdall.core.utils.common.StringUtil;
import cn.heimdall.core.utils.constants.CharsetConstants;
import cn.heimdall.core.utils.enums.Scope;
import cn.heimdall.core.utils.exception.SpiNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class EnhancedServiceLoader {
    public static <S> List<S> loadAll(Class<S> service, Class[] argsType, Object[] args) {
        return InnerEnhancedServiceLoader.getServiceLoader(service).loadAll(argsType, args, findClassLoader());
    }

    public static <S> List<S> loadAll(Class<S> service) {
        return InnerEnhancedServiceLoader.getServiceLoader(service).loadAll(findClassLoader());
    }

    public static <S> S load(Class<S> service, String activateName) throws SpiNotFoundException {
        return InnerEnhancedServiceLoader.getServiceLoader(service).load(activateName, findClassLoader());
    }

    private static ClassLoader findClassLoader() {
        return EnhancedServiceLoader.class.getClassLoader();
    }



    private static class InnerEnhancedServiceLoader<S> {
        private static final Logger LOGGER = LoggerFactory.getLogger(InnerEnhancedServiceLoader.class);
        private static final String SERVICES_DIRECTORY = "META-INF/services/";
        private static final String HEIMDALL_DIRECTORY = "META-INF/heimdall/";
        private final ConcurrentMap<ExtensionDefinition, Holder<Object>> definitionToInstanceMap =
                new ConcurrentHashMap<>();
        private final Holder<List<ExtensionDefinition>> definitionsHolder = new Holder<>();
        private static final ConcurrentMap<Class<?>, InnerEnhancedServiceLoader<?>> SERVICE_LOADERS =
                new ConcurrentHashMap<>();
        private final ConcurrentMap<String, List<ExtensionDefinition>> nameToDefinitionsMap = new ConcurrentHashMap<>();
        private final ConcurrentMap<Class<?>, ExtensionDefinition> classToDefinitionMap = new ConcurrentHashMap<>();

        private final Class<S> type;


        private InnerEnhancedServiceLoader(Class<S> type) {
            this.type = type;
        }

        private static <S> InnerEnhancedServiceLoader<S> getServiceLoader(Class<S> type) {
            if (type == null) {
                throw new IllegalArgumentException("Enhanced Service type == null");
            }
            return (InnerEnhancedServiceLoader<S>) CollectionUtil.computeIfAbsent(SERVICE_LOADERS, type,
                    key -> new InnerEnhancedServiceLoader<>(type));
        }

        private S load(String activateName, ClassLoader loader)
                throws SpiNotFoundException {
            return loadExtension(activateName, loader, null, null);
        }

        private S loadExtension(String activateName, ClassLoader loader, Class[] argTypes,
                                Object[] args) {
            if (StringUtil.isEmpty(activateName)) {
                throw new IllegalArgumentException("the name of service provider for [" + type.getName() + "] name is null");
            }
            try {
                loadAllExtensionClass(loader);
                ExtensionDefinition cachedExtensionDefinition = getCachedExtensionDefinition(activateName);
                return getExtensionInstance(cachedExtensionDefinition, loader, argTypes, args);
            } catch (Throwable e) {
                if (e instanceof SpiNotFoundException) {
                    throw (SpiNotFoundException)e;
                } else {
                    throw new SpiNotFoundException(
                            "not found service provider for : " + type.getName() + " caused by " + e.getCause());
                }
            }
        }

        private ExtensionDefinition getCachedExtensionDefinition(String activateName) {
            List<ExtensionDefinition> definitions = nameToDefinitionsMap.get(activateName);
            return CollectionUtil.getLast(definitions);
        }

        private List<Class> getAllExtensionClass(ClassLoader loader) {
            return loadAllExtensionClass(loader);
        }

        private List<Class> loadAllExtensionClass(ClassLoader loader) {
            List<ExtensionDefinition> definitions = definitionsHolder.get();
            if (definitions == null) {
                synchronized (definitionsHolder) {
                    definitions = definitionsHolder.get();
                    if (definitions == null) {
                        definitions = findAllExtensionDefinition(loader);
                        definitionsHolder.set(definitions);
                    }
                }
            }
            return definitions.stream().map(def -> def.getServiceClass()).collect(Collectors.toList());
        }

        private void loadFile(String dir, ClassLoader loader, List<ExtensionDefinition> extensions)
                throws IOException {
            String fileName = dir + type.getName();
            Enumeration<URL> urls;
            if (loader != null) {
                urls = loader.getResources(fileName);
            } else {
                urls = ClassLoader.getSystemResources(fileName);
            }
            if (urls != null) {
                while (urls.hasMoreElements()) {
                    java.net.URL url = urls.nextElement();
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), CharsetConstants.DEFAULT_CHARSET))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            final int ci = line.indexOf('#');
                            if (ci >= 0) {
                                line = line.substring(0, ci);
                            }
                            line = line.trim();
                            if (line.length() > 0) {
                                try {
                                    ExtensionDefinition extensionDefinition = getUnloadedExtensionDefinition(line, loader);
                                    if (extensionDefinition == null) {
                                        if (LOGGER.isDebugEnabled()) {
                                            LOGGER.debug("The same extension {} has already been loaded, skipped", line);
                                        }
                                        continue;
                                    }
                                    extensions.add(extensionDefinition);
                                } catch (LinkageError | ClassNotFoundException e) {
                                    LOGGER.warn("Load [{}] class fail. {}", line, e.getMessage());
                                }
                            }
                        }
                    } catch (Throwable e) {
                        LOGGER.warn("load clazz instance error: {}", e.getMessage());
                    }
                }
            }
        }

        private boolean isDefinitionContainsClazz(String className, ClassLoader loader) {
            for (Map.Entry<Class<?>, ExtensionDefinition> entry : classToDefinitionMap.entrySet()) {
                if (!entry.getKey().getName().equals(className)) {
                    continue;
                }
                if (Objects.equals(entry.getValue().getServiceClass().getClassLoader(), loader)) {
                    return true;
                }
            }
            return false;
        }

        private ExtensionDefinition getUnloadedExtensionDefinition(String className, ClassLoader loader)
                throws ClassNotFoundException {
            //Check whether the definition has been loaded
            if (!isDefinitionContainsClazz(className, loader)) {
                Class<?> clazz = Class.forName(className, true, loader);
                String serviceName = null;
                Integer priority = 0;
                Scope scope = Scope.SINGLETON;
                LoadLevel loadLevel = clazz.getAnnotation(LoadLevel.class);
                if (loadLevel != null) {
                    serviceName = loadLevel.name();
                    priority = loadLevel.order();
                    scope = loadLevel.scope();
                }
                ExtensionDefinition result = new ExtensionDefinition(serviceName, priority, scope, clazz);
                classToDefinitionMap.put(clazz, result);
                if (serviceName != null) {
                    CollectionUtil.computeIfAbsent(nameToDefinitionsMap, serviceName, e -> new ArrayList<>())
                            .add(result);
                }
                return result;
            }
            return null;
        }


        private List<ExtensionDefinition> findAllExtensionDefinition(ClassLoader loader) {
            List<ExtensionDefinition> extensionDefinitions = new ArrayList<>();
            try {
                loadFile(SERVICES_DIRECTORY, loader, extensionDefinitions);
                loadFile(HEIMDALL_DIRECTORY, loader, extensionDefinitions);
            } catch (IOException e) {
                throw new SpiNotFoundException(e);
            }

            //After loaded all the extensions,sort the caches by order
            if (!nameToDefinitionsMap.isEmpty()) {
                for (List<ExtensionDefinition> definitions : nameToDefinitionsMap.values()) {
                    Collections.sort(definitions, (def1, def2) -> {
                        int o1 = def1.getOrder();
                        int o2 = def2.getOrder();
                        return Integer.compare(o1, o2);
                    });
                }
            }

            if (!extensionDefinitions.isEmpty()) {
                Collections.sort(extensionDefinitions, (definition1, definition2) -> {
                    int o1 = definition1.getOrder();
                    int o2 = definition2.getOrder();
                    return Integer.compare(o1, o2);
                });
            }

            return extensionDefinitions;
        }

        private S getExtensionInstance(ExtensionDefinition definition, ClassLoader loader, Class[] argTypes,
                                       Object[] args) {
            if (definition == null) {
                throw new SpiNotFoundException("not found service provider for : " + type.getName());
            }
            if (Scope.SINGLETON.equals(definition.getScope())) {
                Holder<Object> holder = CollectionUtil.computeIfAbsent(definitionToInstanceMap, definition,
                        key -> new Holder<>());
                Object instance = holder.get();
                if (instance == null) {
                    synchronized (holder) {
                        instance = holder.get();
                        if (instance == null) {
                            instance = createNewExtension(definition, loader, argTypes, args);
                            holder.set(instance);
                        }
                    }
                }
                return (S)instance;
            } else {
                return createNewExtension(definition, loader, argTypes, args);
            }
        }

        private S createNewExtension(ExtensionDefinition definition, ClassLoader loader, Class[] argTypes, Object[] args) {
            Class<?> clazz = definition.getServiceClass();
            try {
                S newInstance = initInstance(clazz, argTypes, args);
                return newInstance;
            } catch (Throwable t) {
                throw new IllegalStateException("Extension instance(definition: " + definition + ", class: " +
                        type + ")  could not be instantiated: " + t.getMessage(), t);
            }
        }

        private S initInstance(Class implClazz, Class[] argTypes, Object[] args)
                throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
            S s = null;
            if (argTypes != null && args != null) {
                // Constructor with arguments
                Constructor<S> constructor = implClazz.getDeclaredConstructor(argTypes);
                s = type.cast(constructor.newInstance(args));
            } else {
                // default Constructor
                s = type.cast(implClazz.newInstance());
            }
            if (s instanceof Initialize) {
                ((Initialize)s).init();
            }
            return s;
        }

        private List<S> loadAll(ClassLoader loader) {
            return loadAll(null, null, loader);
        }

        private List<S> loadAll(Class[] argsType, Object[] args, ClassLoader loader) {
            List<S> allInstances = new ArrayList<>();
            List<Class> allClazzs = getAllExtensionClass(loader);
            if (CollectionUtil.isEmpty(allClazzs)) {
                return allInstances;
            }
            try {
                for (Class clazz : allClazzs) {
                    ExtensionDefinition definition = classToDefinitionMap.get(clazz);
                    allInstances.add(getExtensionInstance(definition, loader, argsType, args));
                }
            } catch (Throwable t) {
                throw new SpiNotFoundException(t);
            }
            return allInstances;
        }

        private static class Holder<T> {
            private volatile T value;

            private void set(T value) {
                this.value = value;
            }

            private T get() {
                return value;
            }
        }

    }
}