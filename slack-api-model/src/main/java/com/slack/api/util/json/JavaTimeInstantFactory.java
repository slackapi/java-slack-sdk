package com.slack.api.util.json;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.regex.Pattern;

public class JavaTimeInstantFactory implements JsonDeserializer<Instant>, JsonSerializer<Instant> {

    private boolean failOnUnknownProperties;

    public JavaTimeInstantFactory() {
        this(false);
    }

    public JavaTimeInstantFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    private Pattern NUMBER_FORMAT = Pattern.compile("-?\\d+(\\.\\d+)?");

    @Override
    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json == null || json.isJsonNull()) {
            return null;
        }
        if (!NUMBER_FORMAT.matcher(json.getAsString()).matches()) {
            if (failOnUnknownProperties) {
                throw new JsonParseException("Unknown instant value data: " + json.getAsString());
            } else {
                return null;
            }
        }
        return Instant.ofEpochMilli(json.getAsLong());
    }

    @Override
    public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toEpochMilli());
    }
}
