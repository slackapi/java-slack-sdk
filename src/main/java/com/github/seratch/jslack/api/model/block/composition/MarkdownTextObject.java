package com.github.seratch.jslack.api.model.block.composition;

import lombok.*;

/**
 * https://api.slack.com/reference/messaging/composition-objects#text
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MarkdownTextObject extends TextObject {
    private final String type = "mrkdwn";
    private String text;
    private boolean verbatim;
}
