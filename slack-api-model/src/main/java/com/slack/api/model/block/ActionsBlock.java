package com.slack.api.model.block;

import com.slack.api.model.block.element.BlockElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * https://docs.slack.dev/reference/block-kit/blocks/actions-block
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionsBlock implements LayoutBlock {
    public static final String TYPE = "actions";
    private final String type = TYPE;
    @Builder.Default
    private List<BlockElement> elements = new ArrayList<>();
    private String blockId;
}
