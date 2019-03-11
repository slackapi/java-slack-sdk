package com.github.seratch.jslack.common.json;

import com.github.seratch.jslack.api.model.block.composition.MarkdownTextObject;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import com.github.seratch.jslack.api.model.block.composition.TextObject;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Factory for deserializing BlockKit 'text object' elements from a
 * {@link com.github.seratch.jslack.api.model.Message chat message response}.
 *
 * @see <a href=
 * "https://api.slack.com/reference/messaging/composition-objects#text">Text
 * Composition Objects documentation</a>
 */
public class GsonTextObjectFactory implements JsonDeserializer<TextObject>, JsonSerializer<TextObject> {
    @Override
    public TextObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get("type");
        final String className = prim.getAsString();
        final Class<? extends TextObject> clazz = getTextObjectClassInstance(className);
        return context.deserialize(jsonObject, clazz);
    }

    @Override
    public JsonElement serialize(TextObject src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    private Class<? extends TextObject> getTextObjectClassInstance(String className) {
        switch (className) {
            case "plain_text":
                return PlainTextObject.class;
            case "mrkdwn":
                return MarkdownTextObject.class;
            default:
                throw new JsonParseException("Unknown text object type: " + className);
        }
    }
}
