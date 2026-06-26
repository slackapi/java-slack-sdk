package com.slack.api.model.block;

import com.slack.api.model.block.composition.TextObject;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.block.element.ImageElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A card is a layout block used to display a compact, structured summary of content. It can be used on
 * its own or grouped together inside a {@link CarouselBlock carousel}. At least one of {@code heroImage},
 * {@code title}, {@code actions}, or {@code body} must be provided.
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
     * A top banner image for the card in the form of an image element.
     * The image URL may be up to 3000 characters and the alt text up to 2000 characters.
     */
    private ImageElement heroImage;

    /**
     * A small icon displayed next to the title and subtitle in the form of an image element.
     * The image URL may be up to 3000 characters and the alt text up to 2000 characters.
     * Mutually exclusive with {@code slackIcon}.
     */
    private ImageElement icon;

    /**
     * The name of a built-in Slack icon displayed next to the title and subtitle.
     * Mutually exclusive with {@code icon}.
     */
    private String slackIcon;

    /**
     * The title of the card. Maximum length for the text in this field is 150 characters.
     */
    private TextObject title;

    /**
     * The subtitle of the card. Maximum length for the text in this field is 150 characters.
     */
    private TextObject subtitle;

    /**
     * The body text of the card. Maximum length for the text in this field is 200 characters.
     */
    private TextObject body;

    /**
     * Secondary text displayed beneath the body of the card. Maximum length for the text in this field
     * is 200 characters.
     */
    private TextObject subtext;

    /**
     * Interactive elements (such as buttons) displayed at the bottom of the card. Up to 3 buttons.
     */
    private List<BlockElement> actions;

    private String blockId;
}
