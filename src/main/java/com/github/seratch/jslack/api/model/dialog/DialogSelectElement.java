package com.github.seratch.jslack.api.model.dialog;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Represents a <a href="https://api.slack.com/dialogs#select_elements">select</a>
 * dialog element<p>
 * <p>
 * Use the {@code select} element for multiple choice selections allowing users to pick a
 * single item from a list. True to web roots, this selection is displayed as a dropdown
 * menu.
 */
@Data
@Builder
public class DialogSelectElement implements DialogElement {

    /**
     * Label displayed to user. Required. No more than 24 characters.
     */
    private String label;

    /**
     * Name of form element. Required. No more than 300 characters.
     */
    private String name;

    /**
     * Type of element.  For a dropdown (select), the type is always
     * {@code select} . It's required.
     *
     * @see <a href="https://api.slack.com/dialogs#elements">Dialog form elements</a>
     */
    private final String type = "select";

    /**
     * A default value for this field.  Must match a value presented in {@link DialogOption}s.
     */
    String value;

    /**
     * A string displayed as needed to help guide users in completing the element.
     * 150 character maximum.
     */
    private String placeholder;

    /**
     * Provide true when the form element is not required. By default, form elements are
     * required.
     */
    private boolean optional;

    /**
     * Provide up to 100 option element attributes. Required for this type.
     */
    private List<DialogOption> options;
}
