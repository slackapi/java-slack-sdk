package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.list.ListRecord;

import java.lang.reflect.Type;

/**
 * The Lists message field is a plain array of message references
 * (List&lt;ListRecord.MessageRef&gt;), so no custom normalization is required — default
 * Gson handling is correct. This adapter is retained (as a pass-through) only because the
 * test GsonFactory registers it; the earlier single-object/array normalization it performed
 * modeled a shape the API does not actually return.
 */
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
        return context.deserialize(json.getAsJsonObject(), NormalizedField.class);
    }

    @Override
    public JsonElement serialize(ListRecord.Field src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, NormalizedField.class);
    }
}
