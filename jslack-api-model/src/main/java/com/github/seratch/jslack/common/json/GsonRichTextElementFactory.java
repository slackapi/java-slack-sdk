package com.github.seratch.jslack.common.json;

import com.github.seratch.jslack.api.model.block.element.*;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * https://api.slack.com/changelog/2019-09-what-they-see-is-what-you-get-and-more-and-less
 */
public class GsonRichTextElementFactory implements
        JsonDeserializer<RichTextElement>,
        JsonSerializer<RichTextElement> {

    @Override
    public RichTextElement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get("type");
        final String className = prim.getAsString();
        final Class<? extends RichTextElement> clazz = detectElementClass(className);
        return context.deserialize(jsonObject, clazz);
    }

    @Override
    public JsonElement serialize(RichTextElement src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    public static Class<? extends RichTextElement> detectElementClass(String className) {
        switch (className) {
            // --------------------------------
            // Elements can be a top-level element
            // --------------------------------
            case RichTextSectionElement.TYPE:
                return RichTextSectionElement.class;
            // --------------------------------
            // Elements under rich_text elements
            // --------------------------------
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
