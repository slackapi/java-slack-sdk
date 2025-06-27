package com.slack.api.model.block.element;

import com.slack.api.model.block.ContextBlockElement;
import com.slack.api.model.block.composition.SlackFileObject;
import lombok.*;

/**
 * https://docs.slack.dev/reference/block-kit/block-elements/image-element
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ImageElement extends BlockElement implements ContextBlockElement {
    public static final String TYPE = "image";
    private final String type = TYPE;

    /**
     * The URL of the image to be displayed.
     */
    private String imageUrl;

    /**
     * A plain-text summary of the image. This should not contain any markup.
     */
    private String altText;

    private String fallback;
    private Integer imageWidth;
    private Integer imageHeight;
    private Integer imageBytes;

    private SlackFileObject slackFile;
}
