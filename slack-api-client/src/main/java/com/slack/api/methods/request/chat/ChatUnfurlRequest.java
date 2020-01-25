package com.slack.api.methods.request.chat;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.Action;
import com.slack.api.model.block.LayoutBlock;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Provide custom unfurl behavior for user-posted URLs
 */
@Data
@Builder
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

    /**
     * Set to `true` or `1` to indicate the user must install your Slack app to trigger unfurls for this domain
     */
    private boolean userAuthRequired;

    /**
     * URL-encoded JSON map with keys set to URLs featured in the the message, pointing to their unfurl message attachments.
     */
    private String rawUnfurls;

    private Map<String, UnfurlDetail> unfurls;

    /**
     * Timestamp of the message to add unfurl behavior to.
     */
    private String ts;

    /**
     * Send users to this custom URL where they will complete authentication in your app to fully trigger unfurling.
     * Value should be properly URL-encoded.
     */
    private String userAuthUrl;

    /**
     * Channel ID of the message
     */
    private String channel;


    // https://api.slack.com/docs/message-link-unfurling#unfurls_parameter
    @Data
    public static class UnfurlDetail {
        private String title;
        private String text;
        private String callbackId;
        private String attachmentType;
        private String fallback;
        private List<Action> actions;

        private List<LayoutBlock> blocks;
    }
}
