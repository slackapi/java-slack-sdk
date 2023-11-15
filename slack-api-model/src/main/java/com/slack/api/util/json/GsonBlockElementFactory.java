package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.block.UnknownBlockElement;
import com.slack.api.model.block.element.*;

import java.lang.reflect.Type;

/**
 * Factory for deserializing BlockKit 'block elements' (buttons, selects,
 * images, menus) from a {@link com.slack.api.model.Message chat
 * message response}.
 *
 * @see <a href="https://api.slack.com/reference/messaging/block-elements">Block Elements</a>
 */
public class GsonBlockElementFactory implements JsonDeserializer<BlockElement>, JsonSerializer<BlockElement> {

    private boolean failOnUnknownProperties;

    public GsonBlockElementFactory() {
        this(false);
    }

    public GsonBlockElementFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public BlockElement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get("type");
        final String typeName = prim.getAsString();
        final Class<? extends BlockElement> clazz = getContextBlockElementClassInstance(typeName);
        return context.deserialize(jsonObject, clazz);
    }

    @Override
    public JsonElement serialize(BlockElement src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    private Class<? extends BlockElement> getContextBlockElementClassInstance(String typeName) {
        switch (typeName) {
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
            case TimePickerElement.TYPE:
                return TimePickerElement.class;
            case DatetimePickerElement.TYPE:
                return DatetimePickerElement.class;
            case PlainTextInputElement.TYPE:
                return PlainTextInputElement.class;
            case RichTextInputElement.TYPE:
                return RichTextInputElement.class;
            case URLTextInputElement.TYPE:
                return URLTextInputElement.class;
            case EmailTextInputElement.TYPE:
                return EmailTextInputElement.class;
            case NumberInputElement.TYPE:
                return NumberInputElement.class;
            case FileInputElement.TYPE:
                return FileInputElement.class;
            case RichTextSectionElement.TYPE:
                return RichTextSectionElement.class;
            case RichTextListElement.TYPE:
                return RichTextListElement.class;
            case RichTextQuoteElement.TYPE:
                return RichTextQuoteElement.class;
            case RichTextPreformattedElement.TYPE:
                return RichTextPreformattedElement.class;
            case RadioButtonsElement.TYPE:
                return RadioButtonsElement.class;
            case CheckboxesElement.TYPE:
                return CheckboxesElement.class;
            case WorkflowButtonElement.TYPE:
                return WorkflowButtonElement.class;
            default:
                if (failOnUnknownProperties) {
                    throw new JsonParseException("Unknown block element type: " + typeName);
                } else {
                    return UnknownBlockElement.class;
                }
        }
    }
}
