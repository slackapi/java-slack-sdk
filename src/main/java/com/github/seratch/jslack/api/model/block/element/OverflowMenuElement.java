package com.github.seratch.jslack.api.model.block.element;

import com.github.seratch.jslack.api.model.block.composition.ConfirmationDialogObject;
import com.github.seratch.jslack.api.model.block.composition.OptionObject;
import lombok.*;

/**
 * https://api.slack.com/reference/messaging/block-elements#overflow
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OverflowMenuElement extends BlockElement {
    private final String type = "overflow";
    private String actionId;
    private OptionObject[] options;
    private ConfirmationDialogObject confirm;
}
