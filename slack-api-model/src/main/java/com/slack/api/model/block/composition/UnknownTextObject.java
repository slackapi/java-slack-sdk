package com.slack.api.model.block.composition;

import lombok.*;

/**
 * https://docs.slack.dev/messaging/migrating-outmoded-message-compositions-to-blocks
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
