package com.slack.api.model.block.element;

import com.slack.api.model.ModelConfigurator;
import com.slack.api.model.block.ContextBlockElement;

import java.util.Arrays;
import java.util.List;

public class BlockElements {

    private BlockElements() {
    }

    public static List<BlockElement> asElements(BlockElement... elements) {
        return Arrays.asList(elements);
    }

    public static List<ContextBlockElement> asContextElements(ContextBlockElement... elements) {
        return Arrays.asList(elements);
    }

    public static List<RichTextElement> asRichTextElements(RichTextElement... elements) {
        return Arrays.asList(elements);
    }

    // ButtonElement

    public static ButtonElement button(ModelConfigurator<ButtonElement.ButtonElementBuilder> configurator) {
        return configurator.configure(ButtonElement.builder()).build();
    }

    // CheckboxesElement

    public static CheckboxesElement checkboxes(ModelConfigurator<CheckboxesElement.CheckboxesElementBuilder> configurator) {
        return configurator.configure(CheckboxesElement.builder()).build();
    }

    // OverflowMenuElement

    public static OverflowMenuElement overflowMenu(ModelConfigurator<OverflowMenuElement.OverflowMenuElementBuilder> configurator) {
        return configurator.configure(OverflowMenuElement.builder()).build();
    }

    // PlainTextInputElement

    public static PlainTextInputElement plainTextInput(ModelConfigurator<PlainTextInputElement.PlainTextInputElementBuilder> configurator) {
        return configurator.configure(PlainTextInputElement.builder()).build();
    }

    // DatePickerElement

    public static DatePickerElement datePicker(ModelConfigurator<DatePickerElement.DatePickerElementBuilder> configurator) {
        return configurator.configure(DatePickerElement.builder()).build();
    }

    // ImageElement

    public static ImageElement image(ModelConfigurator<ImageElement.ImageElementBuilder> configurator) {
        return configurator.configure(ImageElement.builder()).build();
    }

    // NOTE: just as an alias to avoid conflict with Blocks.image()
    public static ImageElement imageElement(ModelConfigurator<ImageElement.ImageElementBuilder> configurator) {
        return image(configurator);
    }

    // RadioButtonsElement

    public static RadioButtonsElement radioButtons(ModelConfigurator<RadioButtonsElement.RadioButtonsElementBuilder> configurator) {
        return configurator.configure(RadioButtonsElement.builder()).build();
    }

    // ------------------------------------------------
    // Select Elements
    // ------------------------------------------------

    // ChannelsSelectElement

    public static ChannelsSelectElement channelsSelect(ModelConfigurator<ChannelsSelectElement.ChannelsSelectElementBuilder> configurator) {
        return configurator.configure(ChannelsSelectElement.builder()).build();
    }

    // ConversationsSelectElement

    public static ConversationsSelectElement conversationsSelect(ModelConfigurator<ConversationsSelectElement.ConversationsSelectElementBuilder> configurator) {
        return configurator.configure(ConversationsSelectElement.builder()).build();
    }

    // ExternalSelectElement

    public static ExternalSelectElement externalSelect(ModelConfigurator<ExternalSelectElement.ExternalSelectElementBuilder> configurator) {
        return configurator.configure(ExternalSelectElement.builder()).build();
    }

    // StaticSelectElement

    public static StaticSelectElement staticSelect(ModelConfigurator<StaticSelectElement.StaticSelectElementBuilder> configurator) {
        return configurator.configure(StaticSelectElement.builder()).build();
    }

    // UsersSelectElement

    public static UsersSelectElement usersSelect(ModelConfigurator<UsersSelectElement.UsersSelectElementBuilder> configurator) {
        return configurator.configure(UsersSelectElement.builder()).build();
    }

    // ------------------------------------------------
    // Multi Select Elements
    // ------------------------------------------------

    // MultiChannelsSelectElement

    public static MultiChannelsSelectElement multiChannelsSelect(ModelConfigurator<MultiChannelsSelectElement.MultiChannelsSelectElementBuilder> configurator) {
        return configurator.configure(MultiChannelsSelectElement.builder()).build();
    }

    // MultiConversationsSelectElement

    public static MultiConversationsSelectElement multiConversationsSelect(ModelConfigurator<MultiConversationsSelectElement.MultiConversationsSelectElementBuilder> configurator) {
        return configurator.configure(MultiConversationsSelectElement.builder()).build();
    }

    // MultiExternalSelectElement

    public static MultiExternalSelectElement multiExternalSelect(ModelConfigurator<MultiExternalSelectElement.MultiExternalSelectElementBuilder> configurator) {
        return configurator.configure(MultiExternalSelectElement.builder()).build();
    }

    // MultiUsersSelectElement

    public static MultiUsersSelectElement multiUsersSelect(ModelConfigurator<MultiUsersSelectElement.MultiUsersSelectElementBuilder> configurator) {
        return configurator.configure(MultiUsersSelectElement.builder()).build();
    }

    // MultiStaticSelectElement

    public static MultiStaticSelectElement multiStaticSelect(ModelConfigurator<MultiStaticSelectElement.MultiStaticSelectElementBuilder> configurator) {
        return configurator.configure(MultiStaticSelectElement.builder()).build();
    }

    // ------------------------------------------------
    // Rich Text Elements
    // ------------------------------------------------

    // RichTextListElement

    public static RichTextListElement richTextList(ModelConfigurator<RichTextListElement.RichTextListElementBuilder> configurator) {
        return configurator.configure(RichTextListElement.builder()).build();
    }

    // RichTextPreformattedElement

    public static RichTextPreformattedElement richTextPreformatted(ModelConfigurator<RichTextPreformattedElement.RichTextPreformattedElementBuilder> configurator) {
        return configurator.configure(RichTextPreformattedElement.builder()).build();
    }

    // RichTextQuoteElement

    public static RichTextQuoteElement richTextQuote(ModelConfigurator<RichTextQuoteElement.RichTextQuoteElementBuilder> configurator) {
        return configurator.configure(RichTextQuoteElement.builder()).build();
    }

    // RichTextSectionElement

    public static RichTextSectionElement richTextSection(ModelConfigurator<RichTextSectionElement.RichTextSectionElementBuilder> configurator) {
        return configurator.configure(RichTextSectionElement.builder()).build();
    }

}
