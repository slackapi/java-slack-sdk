package com.github.seratch.jslack.api.model.block;

import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/reference/messaging/blocks#image
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageBlock implements LayoutBlock {
    public static final String TYPE = "image";
    private final String type = TYPE;
    private String imageUrl;
    private String altText;
    private PlainTextObject title;
    private String blockId;
}
