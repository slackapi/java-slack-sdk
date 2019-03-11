package com.github.seratch.jslack.api.model.block.composition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/reference/messaging/composition-objects#text
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
public class PlainTextObject extends TextObject {
    private final String type = "plain_text";
    private String text;
    private Boolean emoji;
}
