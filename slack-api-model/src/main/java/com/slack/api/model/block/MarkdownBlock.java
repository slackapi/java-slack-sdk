package com.slack.api.model.block;

import com.slack.api.model.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/reference/block-kit/blocks/markdown-block
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarkdownBlock implements LayoutBlock {
    public static final String TYPE = "markdown";
    /**
     * The type of block. For a markdown block, type is always markdown.
     */
    private final String type = TYPE;
    /**
     * The standard markdown-formatted text. Limit 12,000 characters max.
     */
    private String text;
    /**
     * The block_id is ignored in markdown blocks and will not be retained.
     */
    private String blockId;
}
