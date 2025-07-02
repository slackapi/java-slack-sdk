package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/reference/block-kit/blocks
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnknownBlock implements LayoutBlock {
    private String type;
    private String blockId;
}
