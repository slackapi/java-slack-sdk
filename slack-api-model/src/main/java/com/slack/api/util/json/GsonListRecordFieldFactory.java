package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.list.ListRecord;

import java.lang.reflect.Type;
import java.util.Map;

public class GsonListRecordFieldFactory implements JsonDeserializer<ListRecord.Field>, JsonSerializer<ListRecord.Field> {

    static class NormalizedField extends ListRecord.Field {
    }

    private final boolean failOnUnknownProperties;

    public GsonListRecordFieldFactory() {
        this(false);
    }

    public GsonListRecordFieldFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public ListRecord.Field deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // The "message" field is an array of message references, but a single object or a
        // permalink string is wrapped into an array so older payload shapes still parse.
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
            JsonElement value = entry.getValue();
            if ("message".equals(entry.getKey()) && !value.isJsonArray() && !value.isJsonNull()) {
                JsonArray array = new JsonArray();
                array.add(value);
                value = array;
            }
            jsonObject.add(entry.getKey(), value);
        }
        return context.deserialize(jsonObject, NormalizedField.class);
    }

    @Override
    public JsonElement serialize(ListRecord.Field src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, NormalizedField.class);
    }
}
