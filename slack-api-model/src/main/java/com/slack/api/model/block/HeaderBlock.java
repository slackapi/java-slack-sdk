package com.slack.api.model.block;

import com.slack.api.model.block.composition.PlainTextObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/reference/block-kit/blocks/header-block
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeaderBlock implements LayoutBlock {
    public static final String TYPE = "header";
    private final String type = TYPE;

    /**
     * A string acting as a unique identifier for a block. If not specified, one will be generated.
     * Maximum length for this field is 255 characters.
     * block_id should be unique for each message and each iteration of a message.
     * If a message is updated, use a new block_id.
     */
    private String blockId;

    /**
     * The text for the block, in the form of a plain_text text object.
     * Maximum length for the text in this field is 3000 characters.
     */
    private PlainTextObject text;

}
