package com.github.seratch.jslack.common.json;

import com.github.seratch.jslack.SlackConfig;
import com.github.seratch.jslack.api.model.block.ContextBlockElement;
import com.github.seratch.jslack.api.model.block.LayoutBlock;
import com.github.seratch.jslack.api.model.block.composition.TextObject;
import com.github.seratch.jslack.api.model.block.element.BlockElement;
import com.github.seratch.jslack.api.model.block.element.RichTextSectionElement;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {
    private GsonFactory() {
    }

    public static Gson createSnakeCase() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(LayoutBlock.class, new GsonLayoutBlockFactory())
                .registerTypeAdapter(TextObject.class, new GsonTextObjectFactory())
                .registerTypeAdapter(ContextBlockElement.class, new GsonContextBlockElementFactory())
                .registerTypeAdapter(BlockElement.class, new GsonBlockElementFactory())
                .registerTypeAdapter(RichTextSectionElement.Element.class, new GsonRichTextSectionElementFactory())
                .create();
    }

    public static Gson createSnakeCase(SlackConfig config) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(LayoutBlock.class, new GsonLayoutBlockFactory())
                .registerTypeAdapter(TextObject.class, new GsonTextObjectFactory())
                .registerTypeAdapter(ContextBlockElement.class, new GsonContextBlockElementFactory())
                .registerTypeAdapter(BlockElement.class, new GsonBlockElementFactory())
                .registerTypeAdapter(RichTextSectionElement.Element.class, new GsonRichTextSectionElementFactory());
        if (config.isLibraryMaintainerMode()) {
            gsonBuilder = gsonBuilder.registerTypeAdapterFactory(new UnknownPropertyDetectionAdapterFactory());
        }
        if (config.isPrettyResponseLoggingEnabled()) {
            gsonBuilder = gsonBuilder.setPrettyPrinting();
        }
        return gsonBuilder.create();
    }

    public static Gson createCamelCase(SlackConfig config) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(LayoutBlock.class, new GsonLayoutBlockFactory())
                .registerTypeAdapter(TextObject.class, new GsonTextObjectFactory())
                .registerTypeAdapter(ContextBlockElement.class, new GsonContextBlockElementFactory())
                .registerTypeAdapter(BlockElement.class, new GsonBlockElementFactory())
                .registerTypeAdapter(RichTextSectionElement.Element.class, new GsonRichTextSectionElementFactory());
        if (config.isLibraryMaintainerMode()) {
            gsonBuilder = gsonBuilder.registerTypeAdapterFactory(new UnknownPropertyDetectionAdapterFactory());
        }
        if (config.isPrettyResponseLoggingEnabled()) {
            gsonBuilder = gsonBuilder.setPrettyPrinting();
        }
        return gsonBuilder.create();
    }
}
