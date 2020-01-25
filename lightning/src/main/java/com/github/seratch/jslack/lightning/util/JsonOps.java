package com.github.seratch.jslack.lightning.util;

import com.slack.api.util.json.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class JsonOps {

    private static final Gson gson = GsonFactory.createSnakeCase();

    private JsonOps() {
    }

    public static String toJsonString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else {
            return gson.toJson(obj);
        }
    }

    public static JsonElement toJson(Object obj) {
        if (obj instanceof String) {
            return gson.fromJson((String) obj, JsonElement.class);
        } else {
            return gson.toJsonTree(obj);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

}
