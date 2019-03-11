package com.github.seratch.jslack.api.model.block;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/reference/messaging/blocks#divider
 */
@Data
@Builder
@NoArgsConstructor
public class DividerBlock implements LayoutBlock {
    private final String type = "divider";
}
