package com.github.seratch.jslack.api.model.block.element;

import com.github.seratch.jslack.api.model.block.composition.ConfirmationDialogObject;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import lombok.*;

/**
 * https://api.slack.com/reference/messaging/block-elements#channels-select
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelsSelectElement extends BlockElement {
    private static final String TYPE = "channels_select";
    private final String type = TYPE;
    private String fallback;
    private PlainTextObject placeholder;
    private String actionId;
    private String initialChannel;
    private ConfirmationDialogObject confirm;
}
