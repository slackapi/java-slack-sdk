package com.slack.api.model.block.composition;

import lombok.*;

/**
 * https://api.slack.com/reference/messaging/composition-objects#text
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UnknownTextObject extends TextObject {
    private String type;
    private String text;
}
