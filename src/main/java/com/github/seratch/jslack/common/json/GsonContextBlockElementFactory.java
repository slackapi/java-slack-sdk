package com.github.seratch.jslack.common.json;

import com.github.seratch.jslack.api.model.block.ContextBlockElement;
import com.github.seratch.jslack.api.model.block.composition.MarkdownTextObject;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import com.github.seratch.jslack.api.model.block.element.ImageElement;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Factory for deserializing BlockKit 'context block' elements from a
 * {@link com.github.seratch.jslack.api.model.Message chat message response}.
 *
 * @see <a href=
 * "https://api.slack.com/reference/messaging/blocks#context">Context Block
 * documentation</a>
 */
public class GsonContextBlockElementFactory implements JsonDeserializer<ContextBlockElement>, JsonSerializer<ContextBlockElement> {
    @Override
    public ContextBlockElement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get("type");
        final String className = prim.getAsString();
        final Class<? extends ContextBlockElement> clazz = getContextBlockElementClassInstance(className);
        return context.deserialize(jsonObject, clazz);
    }

    @Override
    public JsonElement serialize(ContextBlockElement src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    private Class<? extends ContextBlockElement> getContextBlockElementClassInstance(String className) {
        switch (className) {
            case "image":
                return ImageElement.class;

            // does not defer to GsonTextObjectFactory as not to loose the specific context
            // in which the
            // type needs to be parsed (gives a better error message to the consumer).

            case "plain_text":
                return PlainTextObject.class;
            case "mrkdwn":
                return MarkdownTextObject.class;
            default:
                throw new JsonParseException("Unknown context block element type: " + className);
        }
    }
}
