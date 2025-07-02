package com.slack.api.model.block.composition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/messaging/migrating-outmoded-message-compositions-to-blocks
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionObject {

    /**
     * A text object that defines the text shown in the option on the menu.
     * Overflow, select, and multi-select menus can only use plain_text objects,
     * while radio buttons and checkboxes can use mrkdwn text objects.
     * Maximum length for the text in this field is 75 characters.
     */
    private TextObject text;

    /**
     * The string value that will be passed to your app when this option is chosen.
     * Maximum length for this field is 75 characters.
     */
    private String value;

    /**
     * A plain_text only text object that defines a line of descriptive text shown
     * below the text field beside the radio button.
     * Maximum length for the text object within this field is 75 characters.
     */
    private PlainTextObject description;

    /**
     * A URL to load in the user's browser when the option is clicked.
     * The url attribute is only available in overflow menus.
     * https://docs.slack.dev/reference/block-kit/block-elements/overflow-menu-element
     * <p>
     * Maximum length for this field is 3000 characters.
     * If you're using url, you'll still receive an interaction payload and will need to send an acknowledgement response.
     * A URL to load in the user's browser when the option is clicked.
     */
    private String url;
}
