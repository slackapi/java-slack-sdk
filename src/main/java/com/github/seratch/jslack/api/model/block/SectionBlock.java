package com.github.seratch.jslack.api.model.block;

import com.github.seratch.jslack.api.model.block.composition.TextObject;
import com.github.seratch.jslack.api.model.block.element.BlockElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * https://api.slack.com/reference/messaging/blocks#section
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionBlock implements LayoutBlock {
    private final String type = "section";
    private TextObject text;
    private String blockId;
    @Builder.Default
    private List<TextObject> fields = new ArrayList<>();
    private BlockElement accessory;
}
