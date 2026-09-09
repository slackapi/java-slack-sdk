package com.slack.api.model.block.composition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Defines a built-in Slack icon that can be displayed next to the title and subtitle of a card block.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/composition-objects/slack-icon-object">document</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlackIconObject {
    public static final String TYPE = "icon";
    private final String type = TYPE;

    /**
     * The name of the built-in Slack icon.
     */
    private String name;
}
