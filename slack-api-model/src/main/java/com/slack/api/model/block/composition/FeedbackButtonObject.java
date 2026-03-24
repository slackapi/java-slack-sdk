package com.slack.api.model.block.composition;

import com.slack.api.model.block.composition.PlainTextObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Defines an object containing a feedback button to be used within the {@link FeedbackButtonsElement} block.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackButtonObject {
    /**
     * A text object that defines the button's text. Can only be of type: plain_text. Maximum length for the text in this field is 75 characters.
     */
    private PlainTextObject text;
    /**
     * The value to send along with the interaction payload. Maximum length is 2000 characters.
     */
    private String value;
    /**
     * A label for longer descriptive text about a button element. This label will be read out by screen readers instead of the button text object. Maximum length is 75 characters.
     */
    private String accessibilityLabel;
}
