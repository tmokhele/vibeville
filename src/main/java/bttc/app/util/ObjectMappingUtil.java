package bttc.app.util;

import com.google.gson.Gson;

public class ObjectMappingUtil {

    private ObjectMappingUtil() {

    }

    public static Object mapChats(String jsonString, Object targetObject) {
        Gson g = new Gson();
        return g.fromJson(jsonString, targetObject.getClass());
    }
}
