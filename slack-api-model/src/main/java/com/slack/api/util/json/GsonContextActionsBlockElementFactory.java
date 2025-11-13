package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.block.ContextActionsBlockElement;
import com.slack.api.model.block.UnknownContextActionsBlockElement;
import com.slack.api.model.block.element.FeedbackButtonsElement;
import com.slack.api.model.block.element.IconButtonElement;

import java.lang.reflect.Type;

/**
 * Factory for deserializing BlockKit 'context actions block' elements from a
 * {@link com.slack.api.model.Message chat message response}.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/context-actions-block">Context Actions Block</a>
 */
public class GsonContextActionsBlockElementFactory implements JsonDeserializer<ContextActionsBlockElement>, JsonSerializer<ContextActionsBlockElement> {

    private boolean failOnUnknownProperties;

    public GsonContextActionsBlockElementFactory() {
        this(false);
    }

    public GsonContextActionsBlockElementFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public ContextActionsBlockElement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get("type");
        final String typeName = prim.getAsString();
        final Class<? extends ContextActionsBlockElement> clazz = getContextActionsBlockElementClassInstance(typeName);
        return context.deserialize(jsonObject, clazz);
    }

    @Override
    public JsonElement serialize(ContextActionsBlockElement src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    private Class<? extends ContextActionsBlockElement> getContextActionsBlockElementClassInstance(String typeName) {
        switch (typeName) {
            case FeedbackButtonsElement.TYPE:
                return FeedbackButtonsElement.class;
            case IconButtonElement.TYPE:
                return IconButtonElement.class;
            default:
                if (failOnUnknownProperties) {
                    throw new JsonParseException("Unknown context actions block element type: " + typeName);
                } else {
                    return UnknownContextActionsBlockElement.class;
                }
        }
    }
}
