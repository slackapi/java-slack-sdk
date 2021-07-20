package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.event.MessageChangedEvent;

import java.lang.reflect.Type;

public class GsonMessageChangedEventPreviousMessageFactory implements JsonDeserializer<MessageChangedEvent.PreviousMessage>, JsonSerializer<MessageChangedEvent.PreviousMessage> {

    private static final String REPORT_THIS = "Please report this issue at https://github.com/slackapi/java-slack-sdk/issues";

    private final boolean failOnUnknownProperties;

    public GsonMessageChangedEventPreviousMessageFactory() {
        this(false);
    }

    public GsonMessageChangedEventPreviousMessageFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public MessageChangedEvent.PreviousMessage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        MessageChangedEvent.PreviousMessage result = new MessageChangedEvent.PreviousMessage();
        if (json.isJsonArray()) {
            return result;
        } else if (json.isJsonObject()) {
            result.setMessage(context.deserialize(json, MessageChangedEvent.Message.class));
            return result;
        } else {
            if (failOnUnknownProperties) {
                String message = "The whole value (" + json + ") is unsupported. " + REPORT_THIS;
                throw new JsonParseException(message);
            }
        }
        return result;
    }

    @Override
    public JsonElement serialize(MessageChangedEvent.PreviousMessage src, Type typeOfSrc, JsonSerializationContext context) {
        if (src.getMessage() != null) {
            return context.serialize(src.getMessage());
        } else {
            return new JsonArray();
        }
    }
}
