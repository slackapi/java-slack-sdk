package com.slack.api.model.block;

import com.slack.api.model.block.composition.TextObject;
import com.slack.api.model.block.element.BlockElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://docs.slack.dev/reference/block-kit/blocks/section-block
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionBlock implements LayoutBlock {
    public static final String TYPE = "section";
    private final String type = TYPE;
    private TextObject text;
    private String blockId;
    private List<TextObject> fields;
    private BlockElement accessory;
    private Boolean expand;
}
