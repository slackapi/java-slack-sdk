package com.slack.api.model.block.composition;

import com.slack.api.model.ModelConfigurator;

import java.util.Arrays;
import java.util.List;

public class BlockCompositions {

    private BlockCompositions() {
    }

    public static List<TextObject> asSectionFields(TextObject... elements) {
        return Arrays.asList(elements);
    }

    public static List<OptionGroupObject> asOptionGroups(OptionGroupObject... optionGroups) {
        return Arrays.asList(optionGroups);
    }

    public static List<OptionObject> asOptions(OptionObject... options) {
        return Arrays.asList(options);
    }

    // ConfirmationDialogObject

    public static ConfirmationDialogObject confirmationDialog(
            ModelConfigurator<ConfirmationDialogObject.ConfirmationDialogObjectBuilder> configurator) {
        return configurator.configure(ConfirmationDialogObject.builder()).build();
    }

    // PlainTextObject

    public static PlainTextObject plainText(
            ModelConfigurator<PlainTextObject.PlainTextObjectBuilder> configurator) {
        return configurator.configure(PlainTextObject.builder()).build();
    }

    public static PlainTextObject plainText(String text) {
        return PlainTextObject.builder().text(text).build();
    }

    public static PlainTextObject plainText(String text, boolean emoji) {
        return PlainTextObject.builder().text(text).emoji(emoji).build();
    }

    // MarkdownTextObject

    public static MarkdownTextObject markdownText(ModelConfigurator<MarkdownTextObject.MarkdownTextObjectBuilder> configurator) {
        return configurator.configure(MarkdownTextObject.builder()).build();
    }

    public static MarkdownTextObject markdownText(String text) {
        return MarkdownTextObject.builder().text(text).build();
    }

    // OptionGroupObject

    public static OptionGroupObject optionGroup(ModelConfigurator<OptionGroupObject.OptionGroupObjectBuilder> configurator) {
        return configurator.configure(OptionGroupObject.builder()).build();
    }

    // OptionObject

    public static OptionObject option(ModelConfigurator<OptionObject.OptionObjectBuilder> configurator) {
        return configurator.configure(OptionObject.builder()).build();
    }

    public static OptionObject option(TextObject text, String value) {
        return OptionObject.builder().text(text).value(value).build();
    }

    // DispatchActionConfig

    public static DispatchActionConfig dispatchActionConfig(ModelConfigurator<DispatchActionConfig.DispatchActionConfigBuilder> configurator) {
        return configurator.configure(DispatchActionConfig.builder()).build();
    }

    // FeedbackButtonsObject

    public static FeedbackButtonObject feedbackButton(ModelConfigurator<FeedbackButtonObject.FeedbackButtonObjectBuilder> configurator) {
        return configurator.configure(FeedbackButtonObject.builder()).build();
    }

    // SlackFileObject

    public static SlackFileObject slackFile(ModelConfigurator<SlackFileObject.SlackFileObjectBuilder> configurator) {
        return configurator.configure(SlackFileObject.builder()).build();
    }
}
