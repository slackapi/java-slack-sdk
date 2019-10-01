package com.github.seratch.jslack.common.json;

import com.github.seratch.jslack.api.model.block.element.*;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Factory for deserializing BlockKit 'block elements' (buttons, selects,
 * images, menus) from a {@link com.github.seratch.jslack.api.model.Message chat
 * message response}.
 *
 * @see <a href=
 * "https://api.slack.com/reference/messaging/block-elements">Block
 * Elements documentation</a>
 */
public class GsonBlockElementFactory implements JsonDeserializer<BlockElement>, JsonSerializer<BlockElement> {
    @Override
    public BlockElement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get("type");
        final String className = prim.getAsString();
        final Class<? extends BlockElement> clazz = getContextBlockElementClassInstance(className);
        return context.deserialize(jsonObject, clazz);
    }

    @Override
    public JsonElement serialize(BlockElement src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    private Class<? extends BlockElement> getContextBlockElementClassInstance(String className) {
        switch (className) {
            case ButtonElement.TYPE:
                return ButtonElement.class;
            case ImageElement.TYPE:
                return ImageElement.class;
            case ChannelsSelectElement.TYPE:
                return ChannelsSelectElement.class;
            case UsersSelectElement.TYPE:
                return UsersSelectElement.class;
            case ExternalSelectElement.TYPE:
                return ExternalSelectElement.class;
            case ConversationsSelectElement.TYPE:
                return ConversationsSelectElement.class;
            case StaticSelectElement.TYPE:
                return StaticSelectElement.class;
            case MultiChannelsSelectElement.TYPE:
                return MultiChannelsSelectElement.class;
            case MultiUsersSelectElement.TYPE:
                return MultiUsersSelectElement.class;
            case MultiExternalSelectElement.TYPE:
                return MultiExternalSelectElement.class;
            case MultiConversationsSelectElement.TYPE:
                return MultiConversationsSelectElement.class;
            case MultiStaticSelectElement.TYPE:
                return MultiStaticSelectElement.class;
            case OverflowMenuElement.TYPE:
                return OverflowMenuElement.class;
            case DatePickerElement.TYPE:
                return DatePickerElement.class;
            case PlainTextInputElement.TYPE:
                return PlainTextInputElement.class;
            case RichTextSectionElement.TYPE:
                return RichTextSectionElement.class;
            default:
                throw new JsonParseException("Unknown context block element type: " + className);
        }
    }
}
