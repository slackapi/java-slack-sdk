package com.slack.api.model.block.composition;

import lombok.*;

/**
 * https://api.slack.com/reference/messaging/composition-objects#text
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MarkdownTextObject extends TextObject {
    public static final String TYPE = "mrkdwn";
    private final String type = TYPE;
    private String text;

    /**
     * The documentation of the Slack API states that the verbatim field is optional.
     * The API examples always render the emoji field (as true, but that is its default value) -- so that is not helpful.
     * I picked the Boolean because basically you have 3 possible states:
     * - true
     * - false
     * - not present (and therefore not rendered in the resulting JSON sent to the Slack API)
     */
    private Boolean verbatim;
}
