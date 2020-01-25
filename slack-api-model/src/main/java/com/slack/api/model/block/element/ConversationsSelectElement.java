package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.PlainTextObject;
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
    public static final String TYPE = "conversations_select";
    private final String type = TYPE;
    private String fallback;
    private PlainTextObject placeholder;
    private String actionId;
    private String initialConversation;
    private ConfirmationDialogObject confirm;
}
