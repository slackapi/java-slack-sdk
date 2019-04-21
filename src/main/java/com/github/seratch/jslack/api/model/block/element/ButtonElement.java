package com.github.seratch.jslack.api.model.block.element;

import com.github.seratch.jslack.api.model.block.composition.ConfirmationDialogObject;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import lombok.*;

/**
 * https://api.slack.com/reference/messaging/block-elements#button
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ButtonElement extends BlockElement {
    private static final String TYPE = "button";
    private final String type = TYPE;
    private String fallback;
    private PlainTextObject text;
    private String actionId;
    private String url;
    private String value;
    private String style; // possible values: primary, danger
    private ConfirmationDialogObject confirm;
}
