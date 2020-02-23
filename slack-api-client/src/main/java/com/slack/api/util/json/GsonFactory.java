package com.slack.api.util.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slack.api.SlackConfig;
import com.slack.api.model.block.ContextBlockElement;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.TextObject;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.block.element.RichTextElement;

/**
 * Gson Factory for the entire SDK. This factory enables some Slack-specific settings.
 */
public class GsonFactory {
    private GsonFactory() {
    }

    /**
     * Most of the Slack APIs' key naming is snake-cased.
     */
    public static Gson createSnakeCase() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(LayoutBlock.class, new GsonLayoutBlockFactory())
                .registerTypeAdapter(TextObject.class, new GsonTextObjectFactory())
                .registerTypeAdapter(ContextBlockElement.class, new GsonContextBlockElementFactory())
                .registerTypeAdapter(BlockElement.class, new GsonBlockElementFactory())
                .registerTypeAdapter(RichTextElement.class, new GsonRichTextElementFactory())
                .create();
    }

    /**
     * Most of the Slack APIs' key naming is snake-cased.
     */
    public static Gson createSnakeCase(SlackConfig config) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(LayoutBlock.class, new GsonLayoutBlockFactory(config.isFailOnUnknownProperties()))
                .registerTypeAdapter(TextObject.class, new GsonTextObjectFactory(config.isFailOnUnknownProperties()))
                .registerTypeAdapter(ContextBlockElement.class, new GsonContextBlockElementFactory(config.isFailOnUnknownProperties()))
                .registerTypeAdapter(BlockElement.class, new GsonBlockElementFactory(config.isFailOnUnknownProperties()))
                .registerTypeAdapter(RichTextElement.class, new GsonRichTextElementFactory(config.isFailOnUnknownProperties()));
        if (config.isFailOnUnknownProperties() || config.isLibraryMaintainerMode()) {
            gsonBuilder = gsonBuilder.registerTypeAdapterFactory(new UnknownPropertyDetectionAdapterFactory());
        }
        if (config.isPrettyResponseLoggingEnabled()) {
            gsonBuilder = gsonBuilder.setPrettyPrinting();
        }
        return gsonBuilder.create();
    }

    /**
     * Mainly used for SCIM APIs.
     */
    public static Gson createCamelCase(SlackConfig config) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(LayoutBlock.class, new GsonLayoutBlockFactory(config.isFailOnUnknownProperties()))
                .registerTypeAdapter(TextObject.class, new GsonTextObjectFactory(config.isFailOnUnknownProperties()))
                .registerTypeAdapter(ContextBlockElement.class, new GsonContextBlockElementFactory(config.isFailOnUnknownProperties()))
                .registerTypeAdapter(BlockElement.class, new GsonBlockElementFactory(config.isFailOnUnknownProperties()))
                .registerTypeAdapter(RichTextElement.class, new GsonRichTextElementFactory(config.isFailOnUnknownProperties()));
        if (config.isFailOnUnknownProperties() || config.isLibraryMaintainerMode()) {
            gsonBuilder = gsonBuilder.registerTypeAdapterFactory(new UnknownPropertyDetectionAdapterFactory());
        }
        if (config.isPrettyResponseLoggingEnabled()) {
            gsonBuilder = gsonBuilder.setPrettyPrinting();
        }
        return gsonBuilder.create();
    }
}
