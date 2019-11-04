package com.github.seratch.jslack.common.json;

import com.github.seratch.jslack.api.model.block.element.RichTextSectionElement;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * https://api.slack.com/changelog/2019-09-what-they-see-is-what-you-get-and-more-and-less
 */
public class GsonRichTextSectionElementFactory implements JsonDeserializer<RichTextSectionElement.Element>, JsonSerializer<RichTextSectionElement.Element> {
    @Override
    public RichTextSectionElement.Element deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get("type");
        final String className = prim.getAsString();
        final Class<? extends RichTextSectionElement.Element> clazz = getContextBlockElementClassInstance(className);
        return context.deserialize(jsonObject, clazz);
    }

    @Override
    public JsonElement serialize(RichTextSectionElement.Element src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    private Class<? extends RichTextSectionElement.Element> getContextBlockElementClassInstance(String className) {
        switch (className) {
            case RichTextSectionElement.Text.TYPE:
                return RichTextSectionElement.Text.class;
            case RichTextSectionElement.Channel.TYPE:
                return RichTextSectionElement.Channel.class;
            case RichTextSectionElement.User.TYPE:
                return RichTextSectionElement.User.class;
            case RichTextSectionElement.Emoji.TYPE:
                return RichTextSectionElement.Emoji.class;
            case RichTextSectionElement.Link.TYPE:
                return RichTextSectionElement.Link.class;
            case RichTextSectionElement.Team.TYPE:
                return RichTextSectionElement.Team.class;
            case RichTextSectionElement.UserGroup.TYPE:
                return RichTextSectionElement.UserGroup.class;
            case RichTextSectionElement.Date.TYPE:
                return RichTextSectionElement.Date.class;
            case RichTextSectionElement.Broadcast.TYPE:
                return RichTextSectionElement.Broadcast.class;
            default:
                throw new JsonParseException("Unknown RichTextSectionElement type: " + className);
        }
    }
}
