package com.slack.api.model.block;

import com.slack.api.model.block.element.ImageElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A card is a layout block used to display a compact, structured summary of content. It can be used on its own
 * or grouped together inside a {@link CarouselBlock carousel}. At least one of {@code heroImage}, {@code title},
 * {@code actions}, or {@code body} must be provided.
 *
 * https://docs.slack.dev/reference/block-kit/blocks/card-block
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardBlock implements LayoutBlock {
    public static final String TYPE = "card";
    private final String type = TYPE;

    /**
     * Link to the top image used on the card. Max length of 3000 characters.
     */
    private ImageElement heroImage;

    /**
     * Small image displayed beside the title and subtitle. Max length of 3000 characters.
     * Mutually exclusive with {@code slackIcon}.
     */
    private ImageElement icon;

    /**
     * The name of a built-in Slack icon to display beside the title and subtitle.
     * Mutually exclusive with {@code icon}.
     */
    private String slackIcon;

    /**
     * The title of the card. 150 character maximum.
     */
    private String title;

    /**
     * The subtitle of the card. 150 character maximum.
     */
    private String subtitle;

    /**
     * The main text of the card. 200 character maximum.
     */
    private String body;

    /**
     * Secondary text displayed beneath the body. 200 character maximum.
     */
    private String subtext;

    /**
     * An {@link ActionsBlock actions block} containing a maximum of 3 buttons.
     */
    private ActionsBlock actions;

    private String blockId;
}
