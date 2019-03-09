package com.github.seratch.jslack.api.model.block.element;

import com.github.seratch.jslack.api.model.block.composition.ConfirmationDialogObject;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/reference/messaging/block-elements#channels-select
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelsSelectElement extends BlockElement {
    private final String type = "channels_select";
    private PlainTextObject placeholder;
    private String actionId;
    private String initialChannel;
    private ConfirmationDialogObject confirm;
}
