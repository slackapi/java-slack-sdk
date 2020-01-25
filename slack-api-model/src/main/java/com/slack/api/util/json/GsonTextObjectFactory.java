package com.slack.api.util.json;

import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.composition.TextObject;
import com.slack.api.model.block.composition.UnknownTextObject;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Factory for deserializing BlockKit 'text object' elements from a
 * {@link com.slack.api.model.Message chat message response}.
 *
 * @see <a href=
 * "https://api.slack.com/reference/messaging/composition-objects#text">Text
 * Composition Objects documentation</a>
 */
public class GsonTextObjectFactory implements JsonDeserializer<TextObject>, JsonSerializer<TextObject> {

    private boolean failOnUnknownProperties;

    public GsonTextObjectFactory() {
        this(false);
    }

    public GsonTextObjectFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public TextObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get("type");
        final String typeName = prim.getAsString();
        final Class<? extends TextObject> clazz = getTextObjectClassInstance(typeName);
        return context.deserialize(jsonObject, clazz);
    }

    @Override
    public JsonElement serialize(TextObject src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    private Class<? extends TextObject> getTextObjectClassInstance(String typeName) {
        switch (typeName) {
            case PlainTextObject.TYPE:
                return PlainTextObject.class;
            case MarkdownTextObject.TYPE:
                return MarkdownTextObject.class;
            default:
                if (failOnUnknownProperties) {
                    throw new JsonParseException("Unknown text object type: " + typeName);
                } else {
                    return UnknownTextObject.class;
                }
        }
    }
}
