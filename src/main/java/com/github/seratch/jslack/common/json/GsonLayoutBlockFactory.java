package com.github.seratch.jslack.common.json;

import java.lang.reflect.Type;

import com.github.seratch.jslack.api.model.block.ActionsBlock;
import com.github.seratch.jslack.api.model.block.ContextBlock;
import com.github.seratch.jslack.api.model.block.DividerBlock;
import com.github.seratch.jslack.api.model.block.ImageBlock;
import com.github.seratch.jslack.api.model.block.LayoutBlock;
import com.github.seratch.jslack.api.model.block.SectionBlock;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Factory for deserializing BlockKit elements from a
 * {@link com.github.seratch.jslack.api.model.Message chat message response}.
 */
public class GsonLayoutBlockFactory implements JsonDeserializer<LayoutBlock>, JsonSerializer<LayoutBlock> {
    @Override
    public LayoutBlock deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get("type");
        final String className = prim.getAsString();
        final Class<? extends LayoutBlock> clazz = getLayoutClassInstance(className);
        return context.deserialize(jsonObject, clazz);
    }

    private Class<? extends LayoutBlock> getLayoutClassInstance(String className) {
        switch (className) {
        case "section":
            return SectionBlock.class;
        case "divider":
            return DividerBlock.class;
        case "image":
            return ImageBlock.class;
        case "context":
            return ContextBlock.class;
        case "actions":
            return ActionsBlock.class;
        default:
            throw new JsonParseException("Unsupported layout block type: " + className);
        }
    }

    @Override
    public JsonElement serialize(LayoutBlock src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }
}
