package com.github.seratch.jslack.api.model.dialog;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a <a href="https://api.slack.com/dialogs#textarea_elements">textarea</a>
 * dialog element<p>
 * <p>
 * A {@code textarea} is a multi-line plain text editing control. You've likely encountered
 * these on the world wide web. Use this element if you want a relatively long answer from
 * users.
 */
@Data
@Builder
public class DialogTextAreaElement implements DialogElement {

    /**
     * Label displayed to user. Required. No more than 24 characters.
     */
    private String label;

    /**
     * Name of form element. Required. No more than 300 characters.
     */
    private String name;

    /**
     * Type of element.  For a textarea, the type is always {@code textarea} . It's required.
     *
     * @see <a href="https://api.slack.com/dialogs#elements">Dialog form elements</a>
     */
    private final String type = "textarea";

    /**
     * A default value for this field.  Up to 500 characters.
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
     * Maximum input length allowed for element. 0-500 characters. Defaults to 150.
     */
    public int maxLength;

    /**
     * Minimum input length allowed for element. 1-500 characters. Defaults to 0.
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
