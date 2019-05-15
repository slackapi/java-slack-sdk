package com.github.seratch.jslack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/reference/messaging/blocks#divider
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
