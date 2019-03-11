package com.github.seratch.jslack.api.model.block.composition;

import lombok.*;

/**
 * https://api.slack.com/reference/messaging/composition-objects#text
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlainTextObject extends TextObject {
    private final String type = "plain_text";
    private String text;
    private Boolean emoji;
}
