package cn.heimdall.core.utils.common;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static <K, V> V computeIfAbsent(Map<K, V> map, K key, Function<? super K, ? extends V> mappingFunction) {
        V value = map.get(key);
        if (value != null) {
            return value;
        }
        return map.computeIfAbsent(key, mappingFunction);
    }

    public static <T> T getLast(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }

        int size;
        while (true) {
            size = list.size();
            if (size == 0) {
                return null;
            }

            try {
                return list.get(size - 1);
            } catch (IndexOutOfBoundsException ex) {
                // catch the exception and continue to retry
            }
        }
    }
}
