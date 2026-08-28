package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.block.*;

import java.lang.reflect.Type;

/**
 * Factory for deserializing BlockKit elements from a
 * {@link com.slack.api.model.Message chat message response}.
 */
public class GsonLayoutBlockFactory implements JsonDeserializer<LayoutBlock>, JsonSerializer<LayoutBlock> {

    private final boolean failOnUnknownProperties;

    public GsonLayoutBlockFactory() {
        this(false);
    }

    public GsonLayoutBlockFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public LayoutBlock deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get("type");
        final String typeName = prim.getAsString();
        final Class<? extends LayoutBlock> clazz = getLayoutClassInstance(typeName);
        return context.deserialize(jsonObject, clazz);
    }

    private Class<? extends LayoutBlock> getLayoutClassInstance(String typeName) {
        switch (typeName) {
            case ActionsBlock.TYPE:
                return ActionsBlock.class;
            case AlertBlock.TYPE:
                return AlertBlock.class;
            case CallBlock.TYPE:
                return CallBlock.class;
            case CardBlock.TYPE:
                return CardBlock.class;
            case CarouselBlock.TYPE:
                return CarouselBlock.class;
            case ContextBlock.TYPE:
                return ContextBlock.class;
            case ContextActionsBlock.TYPE:
                return ContextActionsBlock.class;
            case DividerBlock.TYPE:
                return DividerBlock.class;
            case FileBlock.TYPE:
                return FileBlock.class;
            case HeaderBlock.TYPE:
                return HeaderBlock.class;
            case ImageBlock.TYPE:
                return ImageBlock.class;
            case InputBlock.TYPE:
                return InputBlock.class;
            case MarkdownBlock.TYPE:
                return MarkdownBlock.class;
            case RichTextBlock.TYPE:
                return RichTextBlock.class;
            case SectionBlock.TYPE:
                return SectionBlock.class;
            case ShareShortcutBlock.TYPE:
                return ShareShortcutBlock.class;
            case TableBlock.TYPE:
                return TableBlock.class;
            case VideoBlock.TYPE:
                return VideoBlock.class;
            default:
                if (failOnUnknownProperties) {
                    throw new JsonParseException("Unsupported layout block type: " + typeName);
                } else {
                    return UnknownBlock.class;
                }
        }
    }

    @Override
    public JsonElement serialize(LayoutBlock src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }
}
