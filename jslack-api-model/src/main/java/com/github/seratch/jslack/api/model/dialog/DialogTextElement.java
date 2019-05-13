package com.github.seratch.jslack.api.model.dialog;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a <a href="https://api.slack.com/dialogs#text_elements">text</a>
 * dialog element<p>
 * <p>
 * {@code Text} elements are single-line plain text fields.
 */
@Data
@Builder
public class DialogTextElement implements DialogElement {

    /**
     * Label displayed to user. Required. No more than 24 characters.
     */
    private String label;

    /**
     * Name of form element. Required. No more than 300 characters.
     */
    private String name;

    /**
     * Type of element.  For a text element, the type is always
     * {@code text} . It's required.
     *
     * @see <a href="https://api.slack.com/dialogs#elements">Dialog form elements</a>
     */
    private final String type = "text";

    /**
     * A default value for this field. Up to 500 characters.
     */
    String value;

    /**
     * A string displayed as needed to help guide users in completing the element.
     * 150 character maximum.
     */
    private String placeholder;

    /**
     * Provide {@code true} when the form element is not required. By default,
     * form elements are required.
     */
    boolean optional;

    /**
     * Maximum input length allowed for element. Up to 150 characters. Defaults to 150.
     */
    public int maxLength;

    /**
     * Minimum input length allowed for element.
     * Type {@code text}: Up to 150 characters. Defaults to 0.
     */
    int minLength;

    /**
     * Helpful text provided to assist users in answering a question. Up to 150 characters.
     */
    String hint;

    /**
     * Subtype for this text type element (e.g. Number)
     */
    DialogSubType subtype;
}
