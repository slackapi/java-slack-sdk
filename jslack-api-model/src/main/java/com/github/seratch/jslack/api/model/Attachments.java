package com.github.seratch.jslack.api.model;

import java.util.Arrays;
import java.util.List;

public class Attachments {

    private Attachments() {
    }

    public static List<Attachment> asAttachments(Attachment... attachments) {
        return Arrays.asList(attachments);
    }

    public static Attachment attachment(ModelConfigurator<Attachment.AttachmentBuilder> configurator) {
        return configurator.configure(Attachment.builder()).build();
    }

    public static Attachment.AttachmentMetadata attachmentMetadata(ModelConfigurator<Attachment.AttachmentMetadata.AttachmentMetadataBuilder> configurator) {
        return configurator.configure(Attachment.AttachmentMetadata.builder()).build();
    }

    public static Field field(ModelConfigurator<Field.FieldBuilder> configurator) {
        return configurator.configure(Field.builder()).build();
    }

    public static Action action(ModelConfigurator<Action.ActionBuilder> configurator) {
        return configurator.configure(Action.builder()).build();
    }

    public static Confirmation confirm(ModelConfigurator<Confirmation.ConfirmationBuilder> configurator) {
        return configurator.configure(Confirmation.builder()).build();
    }

    public static Action.OptionGroup optionGroup(ModelConfigurator<Action.OptionGroup.OptionGroupBuilder> configurator) {
        return configurator.configure(Action.OptionGroup.builder()).build();
    }

    public static Action.Option option(ModelConfigurator<Action.Option.OptionBuilder> configurator) {
        return configurator.configure(Action.Option.builder()).build();
    }

    public static List<Action> asActions(Action... actions) {
        return Arrays.asList(actions);
    }

    public static List<Field> asFields(Field... fields) {
        return Arrays.asList(fields);
    }

    public static List<Action.OptionGroup> asOptionGroups(Action.OptionGroup... optionGroups) {
        return Arrays.asList(optionGroups);
    }

    public static List<Action.Option> asOptions(Action.Option... options) {
        return Arrays.asList(options);
    }

    public static List<String> asList(String... values) {
        return Arrays.asList(values);
    }

}
