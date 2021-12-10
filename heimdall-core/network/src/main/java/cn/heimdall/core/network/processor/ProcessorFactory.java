package cn.heimdall.core.network.processor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ProcessorFactory {
    public static Object getObject(String className, Class[] argsTypes, Object[] args){
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
            if (argsTypes != null && args != null) {
                Constructor constructor = cls.getDeclaredConstructor(argsTypes);
                return constructor.newInstance(args);
            } else {
                return cls.newInstance();
            }
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
