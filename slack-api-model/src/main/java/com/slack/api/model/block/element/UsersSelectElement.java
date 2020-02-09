package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.*;

/**
 * https://api.slack.com/reference/messaging/block-elements#users-select
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersSelectElement extends BlockElement {
    public static final String TYPE = "users_select";
    private final String type = TYPE;

    private PlainTextObject placeholder;
    private String actionId;
    private String initialUser;
    private ConfirmationDialogObject confirm;
}
