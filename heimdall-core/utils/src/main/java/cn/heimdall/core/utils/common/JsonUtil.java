package cn.heimdall.core.utils.common;

import com.google.gson.Gson;

public final class JsonUtil {

    private static final Gson GSON = new Gson();

    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        return GSON.fromJson(jsonStr, clazz);
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }


}
