package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.Message;
import com.slack.api.model.list.ListRecord;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        JsonObject jsonObject = json.getAsJsonObject();
        // The "message" field can be a single object or an array.
        // Normalize to array before deserializing.
        if (jsonObject.has("message") && jsonObject.get("message").isJsonArray()) {
            JsonArray messageArray = jsonObject.getAsJsonArray("message");
            // Store the full array as "messages" and keep first element as "message"
            jsonObject.remove("message");
            if (messageArray.size() > 0) {
                jsonObject.add("message", messageArray.get(0));
            }
            ListRecord.Field field = context.deserialize(jsonObject, NormalizedField.class);
            List<Message> messages = new ArrayList<>();
            for (JsonElement element : messageArray) {
                messages.add(context.deserialize(element, Message.class));
            }
            field.setMessages(messages);
            return field;
        }
        // Single object or absent — standard deserialization
         ListRecord.Field field = context.deserialize(jsonObject, NormalizedField.class);
        if (field.getMessage() != null) {
            field.setMessages(Collections.singletonList(field.getMessage()));
        }
        return field;
    }

      @Override
      public JsonElement serialize(ListRecord.Field src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, NormalizedField.class);
    }
}