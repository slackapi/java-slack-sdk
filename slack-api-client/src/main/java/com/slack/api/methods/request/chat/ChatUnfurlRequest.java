package com.slack.api.methods.request.chat;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.Action;
import com.slack.api.model.Field;
import com.slack.api.model.EntityMetadata;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * https://docs.slack.dev/reference/methods/chat.unfurl
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatUnfurlRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `links:write`
     */
    private String token;

    /**
     * Provide a simply-formatted string to send as an ephemeral message to the user
     * as invitation to authenticate further and enable full unfurling behavior
     */
    private String userAuthMessage;

    private String rawUserAuthBlocks;

    /**
     * Provide an array of blocks to send as an ephemeral message to the user
     * as invitation to authenticate further and enable full unfurling behavior
     */
    private List<LayoutBlock> userAuthBlocks;

    /**
     * Set to `true` or `1` to indicate the user must install your Slack app to trigger unfurls for this domain
     */
    private boolean userAuthRequired;

    /**
     * Send users to this custom URL where they will complete authentication in your app to fully trigger unfurling.
     * Value should be properly URL-encoded.
     */
    private String userAuthUrl;

    /**
     * URL-encoded JSON map with keys set to URLs featured in the message, pointing to their unfurl message attachments.
     */
    private String rawUnfurls;

    private Map<String, UnfurlDetail> unfurls;

    /**
     * Timestamp of the message to add unfurl behavior to.
     */
    private String ts;

    /**
     * Channel ID of the message
     */
    private String channel;

    // https://docs.slack.dev/changelog/2021-08-changes-to-unfurls
    private String unfurlId;

    // https://docs.slack.dev/changelog/2021-08-changes-to-unfurls
    private String source;

    private String rawMetadata;
    private UnfurlMetadata metadata;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnfurlMetadata {
        private EntityMetadata[] entities;
    }

    // https://docs.slack.dev/messaging/unfurling-links-in-messages
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnfurlDetail {

        private String text;

        // attachments: https://docs.slack.dev/legacy/legacy-messaging/legacy-secondary-message-attachments#guidelines
        private String callbackId;
        private String attachmentType;
        private String fallback;
        private String color;
        private String url;
        private String title;
        private String titleLink;
        private String imageUrl;
        private String authorName;
        private String authorIcon;
        private String authorLink;
        private List<Action> actions;
        private List<Field> fields;

        // blocks: https://docs.slack.dev/reference/block-kit/blocks
        private List<LayoutBlock> blocks;
        // preview: https://docs.slack.dev/reference/methods/chat.unfurl
        private UnfurlDetailPreview preview;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnfurlDetailPreview {
        private PlainTextObject title;
        private PlainTextObject subtitle;
        private String iconUrl;
    }
}
