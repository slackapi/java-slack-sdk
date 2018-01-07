package com.github.seratch.jslack.api.model.dialog;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Represents a Slack Modal Dialog
 *
 * @see <a href="https://api.slack.com/dialogs">Slack Modal Dialog</a>
 */
@Data
@Builder
public class Dialog {

    /**
     * User-facing title of this entire dialog. 24 characters to work with and it's required.
     */
    private String title;

    /**
     * An identifier strictly for you to recognize submissions of this particular instance of
     * a dialog. Use something meaningful to your app. 255 characters maximum.
     * Absolutely required.
     */
    private String callbackId;

    /**
     * Up to 5 form elements are allowed per dialog. Required.
     */
    private List<DialogElement> elements;

    /**
     * User-facing string for whichever button-like thing submits the form, depending on
     * form factor. Defaults to {@code Submit}, localized in whichever language the end user
     * prefers. 24 characters maximum, and may contain only a single word.
     */
    private String submitLabel;
}
