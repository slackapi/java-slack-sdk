package com.slack.api.model.block;

import com.slack.api.model.block.composition.PlainTextObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/reference/block-kit/blocks#video
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoBlock implements LayoutBlock {
    public static final String TYPE = "video";
    private final String type = TYPE;

    /**
     * Video title in plain text format. Must be less than 200 characters.
     */
    private PlainTextObject title;

    /**
     * Hyperlink for the title text. Must correspond to the non-embeddable URL for the video. Must go to an HTTPS URL.
     */
    private String titleUrl;

    /**
     * Description for video in plain text format.
     */
    private PlainTextObject description;

    /**
     * The URL to be embedded. Must match any existing unfurl domains within the app and point to an HTTPS URL.
     * https://api.slack.com/reference/messaging/link-unfurling#configuring_domains
     */
    private String videoUrl;

    /**
     * A tooltip for the video. Required for accessibility
     */
    private String altText;

    /**
     * The thumbnail image URL
     */
    private String thumbnailUrl;

    /**
     * Author name to be displayed. Must be less than 50 characters.
     */
    private String authorName;

    /**
     * The originating application or domain of the video ex. YouTube
     */
    private String providerName;

    /**
     * Icon for the video provider - ex. YouTube icon
     */
    private String providerIconUrl;

    private String blockId;
}
