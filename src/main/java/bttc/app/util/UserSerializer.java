package bttc.app.util;

import bttc.app.model.User;
import com.google.gson.*;

import java.lang.reflect.Type;

public class UserSerializer implements JsonSerializer<Object> {
    @Override
    public JsonElement serialize(Object user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jObj = (JsonObject)new GsonBuilder().create().toJsonTree(user);
        jObj.remove("password");
        jObj.remove("username");
        return jObj;
    }
}
