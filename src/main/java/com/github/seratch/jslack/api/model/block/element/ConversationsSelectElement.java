package com.github.seratch.jslack.api.model.block.element;

import com.github.seratch.jslack.api.model.block.composition.ConfirmationDialogObject;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import lombok.*;

/**
 * https://api.slack.com/reference/messaging/block-elements#conversations-select
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationsSelectElement extends BlockElement {
    private final String type = "conversations_select";
    private PlainTextObject placeholder;
    private String actionId;
    private String initialConversation;
    private ConfirmationDialogObject confirm;
}
