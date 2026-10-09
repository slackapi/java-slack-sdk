package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.list.ListRecord.MessageRef;

import java.lang.reflect.Type;

/**
 * Direction-aware (de)serialization for the Slack Lists message field element.
 *
 * The field is asymmetric on the wire (verified against the live API):
 * <ul>
 *   <li>Request: an array of message permalink URL <b>strings</b> — e.g. {@code "message": ["https://.../p123"]}.</li>
 *   <li>Response: an array of message reference <b>objects</b> — {@code {"value","channel_id","ts","thread_ts"?}}.</li>
 * </ul>
 *
 * This adapter lets a single {@code List<MessageRef>} model both directions: it serializes a
 * MessageRef to its {@code value} (the permalink string) so requests carry the string array the
 * API expects, and it deserializes either an object (normal response) or a bare string (defensive)
 * back into a MessageRef.
 */
public class GsonListRecordMessageRefFactory implements JsonDeserializer<MessageRef>, JsonSerializer<MessageRef> {

    private final boolean failOnUnknownProperties;

    public GsonListRecordMessageRefFactory() {
        this(false);
    }

    public GsonListRecordMessageRefFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public MessageRef deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json == null || json.isJsonNull()) {
            return null;
        }
        MessageRef ref = new MessageRef();
        if (json.isJsonPrimitive()) {
            // Request-shaped or degenerate value: a bare permalink string.
            ref.setValue(json.getAsString());
            return ref;
        }
        if (json.isJsonObject()) {
            JsonObject obj = json.getAsJsonObject();
            if (obj.has("value") && !obj.get("value").isJsonNull()) {
                ref.setValue(obj.get("value").getAsString());
            }
            if (obj.has("channel_id") && !obj.get("channel_id").isJsonNull()) {
                ref.setChannelId(obj.get("channel_id").getAsString());
            }
            if (obj.has("ts") && !obj.get("ts").isJsonNull()) {
                ref.setTs(obj.get("ts").getAsString());
            }
            if (obj.has("thread_ts") && !obj.get("thread_ts").isJsonNull()) {
                ref.setThreadTs(obj.get("thread_ts").getAsString());
            }
            return ref;
        }
        return null;
    }

    @Override
    public JsonElement serialize(MessageRef src, Type typeOfSrc, JsonSerializationContext context) {
        // The request side expects the message field as an array of permalink URL strings.
        if (src == null || src.getValue() == null) {
            return JsonNull.INSTANCE;
        }
        return new JsonPrimitive(src.getValue());
    }
}
