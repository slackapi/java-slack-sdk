package com.github.seratch.jslack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/reference/messaging/blocks
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnknownBlock implements LayoutBlock {
    private String type;
    private String blockId;
}
