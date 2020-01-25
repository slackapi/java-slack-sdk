package com.github.seratch.jslack.api.model.block.element;

import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import lombok.*;

/**
 * https://api.slack.com/reference/block-kit/block-elements#input
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlainTextInputElement extends BlockElement {
    public static final String TYPE = "plain_text_input";
    private final String type = TYPE;
    private String actionId;
    private PlainTextObject placeholder;
    private String initialValue;
    private boolean multiline;
    private Integer minLength;
    private Integer maxLength;
}
