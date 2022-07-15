package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.block.ContextBlockElement;
import com.slack.api.model.block.UnknownContextBlockElement;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.ImageElement;

import java.lang.reflect.Type;

/**
 * Factory for deserializing BlockKit 'context block' elements from a
 * {@link com.slack.api.model.Message chat message response}.
 *
 * @see <a href="https://api.slack.com/reference/messaging/blocks#context">Context Blocks</a>
 */
public class GsonContextBlockElementFactory implements JsonDeserializer<ContextBlockElement>, JsonSerializer<ContextBlockElement> {

    private boolean failOnUnknownProperties;

    public GsonContextBlockElementFactory() {
        this(false);
    }

    public GsonContextBlockElementFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public ContextBlockElement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get("type");
        final String typeName = prim.getAsString();
        final Class<? extends ContextBlockElement> clazz = getContextBlockElementClassInstance(typeName);
        return context.deserialize(jsonObject, clazz);
    }

    @Override
    public JsonElement serialize(ContextBlockElement src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    private Class<? extends ContextBlockElement> getContextBlockElementClassInstance(String typeName) {
        switch (typeName) {
            case ImageElement.TYPE:
                return ImageElement.class;

            // does not defer to GsonTextObjectFactory as not to lose the specific context
            // in which the type needs to be parsed (gives a better error message to the consumer).

            case PlainTextObject.TYPE:
                return PlainTextObject.class;
            case MarkdownTextObject.TYPE:
                return MarkdownTextObject.class;
            default:
                if (failOnUnknownProperties) {
                    throw new JsonParseException("Unknown context block element type: " + typeName);
                } else {
                    return UnknownContextBlockElement.class;
                }
        }
    }
}
