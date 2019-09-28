package com.github.seratch.jslack.api.model.block.element;

import lombok.*;

/**
 * https://api.slack.com/reference/messaging/block-elements#plain-text-input
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
    private Label label;
    private boolean multiline;

    @Data
    public static class Label {
        private String type;
        private String text;
    }
}
