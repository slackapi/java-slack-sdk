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
public class ConfirmationDialogObject {
    private PlainTextObject title;
    private TextObject text;
    private PlainTextObject confirm;
    private PlainTextObject deny;
    private String style;
}
