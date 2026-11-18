package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.Message;
import com.slack.api.model.list.ListRecord;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
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
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
            if (!"message".equals(entry.getKey())) {
                jsonObject.add(entry.getKey(), entry.getValue());
            }
        }
        JsonElement message = json.getAsJsonObject().get("message");
        ListRecord.Field field = context.deserialize(jsonObject, NormalizedField.class);
        if (message == null || message.isJsonNull()) {
            return field;
        }
        // The "message" field can be an array, a single object, or a permalink string.
        // Keep all of them as "messages" and the first one as "message".
        List<Message> messages = new ArrayList<>();
        if (message.isJsonArray()) {
            for (JsonElement element : message.getAsJsonArray()) {
                messages.add(toMessage(element, context));
            }
        } else {
            messages.add(toMessage(message, context));
        }
        field.setMessages(messages);
        return field;
    }

    private static Message toMessage(JsonElement element, JsonDeserializationContext context) {
        if (element.isJsonPrimitive()) {
            Message message = new Message();
            message.setPermalink(element.getAsString());
            return message;
        }
        return context.deserialize(element, Message.class);
    }

    @Override
    public JsonElement serialize(ListRecord.Field src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, NormalizedField.class);
    }
}
