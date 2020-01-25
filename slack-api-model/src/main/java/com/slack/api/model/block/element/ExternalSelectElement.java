package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.OptionObject;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.*;

/**
 * https://api.slack.com/reference/messaging/block-elements#external-select
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalSelectElement extends BlockElement {
    public static final String TYPE = "external_select";
    private final String type = TYPE;
    private String fallback;
    private PlainTextObject placeholder;
    private String actionId;
    private OptionObject initialOption;
    private Integer minQueryLength;
    private ConfirmationDialogObject confirm;
}
