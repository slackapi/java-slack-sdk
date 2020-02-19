package com.slack.api.lightning.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.slack.api.util.json.GsonFactory;

/**
 * Common JSON utilities.
 */
public class JsonOps {

    private static final Gson GSON = GsonFactory.createSnakeCase();

    private JsonOps() {
    }

    public static String toJsonString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else {
            return GSON.toJson(obj);
        }
    }

    public static JsonElement toJson(Object obj) {
        if (obj instanceof String) {
            return GSON.fromJson((String) obj, JsonElement.class);
        } else {
            return GSON.toJsonTree(obj);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

}
