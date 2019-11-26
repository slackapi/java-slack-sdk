package com.github.seratch.jslack.api.model.dialog;

import com.github.seratch.jslack.api.model.ModelConfigurator;

import java.util.Arrays;
import java.util.List;

public class Dialogs {

    private Dialogs() {}

    public static List<DialogElement> asElements(DialogElement... elements) {
        return Arrays.asList(elements);
    }

    public static List<DialogOption> asOptions(DialogOption... options) {
        return Arrays.asList(options);
    }

    public static Dialog dialog(ModelConfigurator<Dialog.DialogBuilder> configurator) {
        return configurator.configure(Dialog.builder()).build();
    }

    public static DialogOption dialogOption(ModelConfigurator<DialogOption.DialogOptionBuilder> configurator) {
        return configurator.configure(DialogOption.builder()).build();
    }

    public static DialogSelectElement dialogSelect(ModelConfigurator<DialogSelectElement.DialogSelectElementBuilder> configurator) {
        return configurator.configure(DialogSelectElement.builder()).build();
    }

    public static DialogTextElement dialogText(ModelConfigurator<DialogTextElement.DialogTextElementBuilder> configurator) {
        return configurator.configure(DialogTextElement.builder()).build();
    }

    public static DialogTextAreaElement dialogTextArea(ModelConfigurator<DialogTextAreaElement.DialogTextAreaElementBuilder> configurator) {
        return configurator.configure(DialogTextAreaElement.builder()).build();
    }

}
