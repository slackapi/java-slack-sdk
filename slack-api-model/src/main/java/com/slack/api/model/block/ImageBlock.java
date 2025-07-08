package com.slack.api.model.block;

import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.composition.SlackFileObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/reference/block-kit/blocks/image-block
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageBlock implements LayoutBlock {
    public static final String TYPE = "image";
    private final String type = TYPE;
    private String fallback;

    private String imageUrl;
    private Integer imageWidth;
    private Integer imageHeight;
    private Integer imageBytes;
    private Boolean isAnimated;

    private SlackFileObject slackFile;

    private String altText;
    private PlainTextObject title;
    private String blockId;
}
