package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/reference/block-kit/blocks/divider-block
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DividerBlock implements LayoutBlock {
    public static final String TYPE = "divider";
    private final String type = TYPE;
    private String blockId;
}
