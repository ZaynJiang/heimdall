package cn.heimdall.core.utils.common;

public class ObjectUtils {
    public static boolean equals(Object object1, Object object2) {
        if (object1 == object2) {
            return true;
        } else {
            return object1 != null && object2 != null ? object1.equals(object2) : false;
        }
    }

    public static boolean notEqual(Object object1, Object object2) {
        return !equals(object1, object2);
    }}
