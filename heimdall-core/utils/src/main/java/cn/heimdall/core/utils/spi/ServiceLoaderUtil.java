package cn.heimdall.core.utils.spi;

import java.util.ServiceLoader;

public class ServiceLoaderUtil {

    private static final String CLASSLOADER_DEFAULT = "default";
    private static final String CLASSLOADER_CONTEXT = "context";

    private ServiceLoaderUtil() {}

    public static <S> ServiceLoader<S> getServiceLoader(Class<S> clazz) {
        if (shouldUseContextClassloader()) {
            return ServiceLoader.load(clazz);
        } else {
            return ServiceLoader.load(clazz, clazz.getClassLoader());
        }
    }

    public static boolean shouldUseContextClassloader() {
        //TODO
        return false;
    }


}
