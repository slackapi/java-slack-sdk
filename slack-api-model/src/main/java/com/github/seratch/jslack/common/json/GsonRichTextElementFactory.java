package com.github.seratch.jslack.common.json;

import com.github.seratch.jslack.api.model.block.element.RichTextElement;
import com.github.seratch.jslack.api.model.block.element.RichTextSectionElement;
import com.github.seratch.jslack.api.model.block.element.RichTextUnknownElement;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * https://api.slack.com/changelog/2019-09-what-they-see-is-what-you-get-and-more-and-less
 */
public class GsonRichTextElementFactory implements
        JsonDeserializer<RichTextElement>,
        JsonSerializer<RichTextElement> {

    private final boolean failOnUnknownProperties;

    public GsonRichTextElementFactory() {
        this(false);
    }

    public GsonRichTextElementFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public RichTextElement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get("type");
        final String typeName = prim.getAsString();
        final Class<? extends RichTextElement> clazz = detectElementClassFromType(typeName);
        return context.deserialize(jsonObject, clazz);
    }

    @Override
    public JsonElement serialize(RichTextElement src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    // to be compatible with version 3.3.0 or older versions
    private static final GsonRichTextElementFactory LEGACY_SINGLETON = new GsonRichTextElementFactory(true);

    @Deprecated
    public static Class<? extends RichTextElement> detectElementClass(String className) {
        return LEGACY_SINGLETON.detectElementClassFromType(className);
    }

    private Class<? extends RichTextElement> detectElementClassFromType(String typeName) {
        switch (typeName) {
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
            case RichTextSectionElement.Color.TYPE:
                return RichTextSectionElement.Color.class;
            default:
                if (failOnUnknownProperties) {
                    throw new JsonParseException("Unknown RichTextSectionElement type: " + typeName);
                } else {
                    return RichTextUnknownElement.class;
                }
        }
    }

}
