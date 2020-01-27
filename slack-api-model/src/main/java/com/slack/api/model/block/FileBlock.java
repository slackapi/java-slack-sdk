package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/reference/messaging/blocks#file
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileBlock implements LayoutBlock {
    public static final String TYPE = "file";
    // The type of block. For a context block, type is always file.
    private final String type = TYPE;
    // A string acting as a unique identifier for a block.
    // You can use this block_id when you receive an interaction payload to identify the source of the action.
    // If not specified, a block_id will be generated. Maximum length for this field is 255 characters.
    private String blockId;
    // 	The external unique ID for this file.
    private String externalId;
    // 	At the moment, source will always be remote for a remote file.
    private String source; // "remote"
}
