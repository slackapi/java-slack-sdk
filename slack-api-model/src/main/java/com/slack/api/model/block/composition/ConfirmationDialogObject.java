package com.slack.api.model.block.composition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Defines a dialog that adds a confirmation step to interactive elements.
 *
 * https://docs.slack.dev/reference/block-kit/composition-objects/confirmation-dialog-object/
 * https://docs.slack.dev/messaging/migrating-outmoded-message-compositions-to-blocks
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationDialogObject {
    /**
     * A plain_text text object that defines the dialog's title. Maximum length for this field is 100 characters.
     */
    private PlainTextObject title;
    /**
     * A plain_text text object that defines the explanatory text that appears in the confirm dialog. Maximum length for the text in this field is 300 characters.
     */
    private TextObject text;
    /**
     * A plain_text text object to define the text of the button that confirms the action. Maximum length for the text in this field is 30 characters.
     */
    private PlainTextObject confirm;
    /**
     * A plain_text text object to define the text of the button that cancels the action. Maximum length for the text in this field is 30 characters.
     */
    private PlainTextObject deny;
    /**
     * Defines the color scheme applied to the confirm button. A value of danger will display the button with a red background on desktop, or red text on mobile. A value of primary will display the button with a green background on desktop, or blue text on mobile. If this field is not provided, the default value will be primary.
     */
    private String style;
}
