package com.slack.api.model.block.composition;

import lombok.*;

/**
 * https://api.slack.com/reference/messaging/composition-objects#text
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UnknownTextObject extends TextObject {
    private String type;
    private String text;
}
