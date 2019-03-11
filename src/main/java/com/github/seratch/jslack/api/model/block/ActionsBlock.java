package com.github.seratch.jslack.api.model.block;

import java.util.ArrayList;
import java.util.List;

import com.github.seratch.jslack.api.model.block.element.BlockElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/reference/messaging/blocks#actions
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionsBlock implements LayoutBlock {
    private final String type = "actions";
    @Builder.Default
    private List<BlockElement> elements = new ArrayList<>();
    private String blockId;
}
