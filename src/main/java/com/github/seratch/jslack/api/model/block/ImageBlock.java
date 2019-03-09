package com.github.seratch.jslack.api.model.block;

import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * https://api.slack.com/reference/messaging/blocks#image
 */
@Data
@Builder
public class ImageBlock implements LayoutBlock {
    private final String type = "image";
    private String imageUrl;
    private String altText;
    private PlainTextObject title;
    private String blockId;
}
