package com.github.seratch.jslack.api.model.block.element;

import com.github.seratch.jslack.api.model.block.composition.ConfirmationDialogObject;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import lombok.*;

/**
 * https://api.slack.com/reference/messaging/block-elements#datepicker
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatePickerElement extends BlockElement {
    private static final String TYPE = "datepicker";
    private final String type = TYPE;
    private String fallback;
    private String actionId;
    private PlainTextObject placeholder;
    private String initialDate;
    private ConfirmationDialogObject confirm;
}
