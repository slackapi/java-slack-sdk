package com.github.seratch.jslack.api.model.block;

import java.util.List;

import com.github.seratch.jslack.api.model.block.composition.TextObject;
import com.github.seratch.jslack.api.model.block.element.BlockElement;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * https://api.slack.com/reference/messaging/blocks#section
 */
@Data
@Builder
public class SectionBlock implements LayoutBlock {
    private final String type = "section";
    private TextObject text;
    private String blockId;
    private List<TextObject> fields;
    private BlockElement accessory;
}
