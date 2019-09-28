package com.github.seratch.jslack.api.model.block;

import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import com.github.seratch.jslack.api.model.block.element.BlockElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/reference/messaging/blocks#input
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputBlock implements LayoutBlock {
    public static final String TYPE = "input";
    private final String type = TYPE;
    private String blockId;
    private PlainTextObject label;
    private boolean optional;
    private BlockElement element;
}
