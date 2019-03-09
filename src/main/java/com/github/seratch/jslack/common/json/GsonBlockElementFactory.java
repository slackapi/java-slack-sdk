package com.github.seratch.jslack.common.json;

import java.lang.reflect.Type;

import com.github.seratch.jslack.api.model.block.element.BlockElement;
import com.github.seratch.jslack.api.model.block.element.ButtonElement;
import com.github.seratch.jslack.api.model.block.element.ChannelsSelectElement;
import com.github.seratch.jslack.api.model.block.element.ConversationsSelectElement;
import com.github.seratch.jslack.api.model.block.element.DatePickerElement;
import com.github.seratch.jslack.api.model.block.element.ExternalSelectElement;
import com.github.seratch.jslack.api.model.block.element.ImageElement;
import com.github.seratch.jslack.api.model.block.element.OverflowMenuElement;
import com.github.seratch.jslack.api.model.block.element.StaticSelectElement;
import com.github.seratch.jslack.api.model.block.element.UsersSelectElement;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Factory for deserializing BlockKit 'block elements' (buttons, selects,
 * images, menus) from a {@link com.github.seratch.jslack.api.model.Message chat
 * message response}.
 * 
 * @see <a href=
 *      "https://api.slack.com/reference/messaging/block-elements">Block
 *      Elements documentation</a>
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
        case "button":
            return ButtonElement.class;
        case "image":
            return ImageElement.class;
        case "channels_select":
            return ChannelsSelectElement.class;
        case "users_select":
            return UsersSelectElement.class;
        case "external_select":
            return ExternalSelectElement.class;
        case "conversations_select":
            return ConversationsSelectElement.class;
        case "static_select":
            return StaticSelectElement.class;
        case "overflow":
            return OverflowMenuElement.class;
        case "datepicker":
            return DatePickerElement.class;
        default:
            throw new JsonParseException("Unknown context block element type: " + className);
        }
    }
}
