package com.slack.api.model.block.composition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/reference/messaging/composition-objects#option
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionObject {

    /**
     * The formatting to use for this text object. Can be one of plain_text or mrkdwn.
     */
    private PlainTextObject text;

    /**
     *	The text for the block. This field accepts any of the standard text formatting markup when type is mrkdwn.
     */
    private String value;

    /**
     * A plain_text https://api.slack.com/reference/block-kit/composition-objects#text
     * only text object that defines a line of descriptive text shown below the text field beside the radio button.
     * Maximum length for the text object within this field is 75 characters.
     */
    private PlainTextObject description;

    /**
     * A URL to load in the user's browser when the option is clicked.
     * The url attribute is only available in overflow menus.
     * https://api.slack.com/reference/block-kit/block-elements#overflow
     * <p>
     * Maximum length for this field is 3000 characters.
     * If you're using url, you'll still receive an interaction payload and will need to send an acknowledgement response.
     */
    private String url;
}
