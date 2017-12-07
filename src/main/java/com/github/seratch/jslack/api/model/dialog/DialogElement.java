package com.github.seratch.jslack.api.model.dialog;

/**
 * A dialog Form DialogElement such as {@code text}, {@code textarea}, or {@code select}
 * or {@code select}.
 *
 * @see <a href="https://api.slack.com/dialogs">Slack Modal Dialog</a>
 */
public interface DialogElement {

    String getLabel();

    void setLabel(String label);

    String getName();

    void setName(String name);

    String getType();


    String getValue();

    void setValue(String value);

    String getPlaceholder();

    void setPlaceholder(String placeholder);

    boolean isOptional();

    void setOptional(boolean isOptional);
}
