package com.github.seratch.jslack.api.model.block.element;

import com.github.seratch.jslack.api.model.block.ContextBlockElement;
import lombok.*;

/**
 * https://api.slack.com/reference/messaging/block-elements#image
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageElement extends BlockElement implements ContextBlockElement {
    private final String type = "image";
    private String imageUrl;
    private String altText;
}
