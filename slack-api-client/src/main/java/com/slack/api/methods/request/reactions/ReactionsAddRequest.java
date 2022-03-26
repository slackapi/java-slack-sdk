package com.slack.api.methods.request.reactions;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/reactions.add
 */
@Data
@Builder
public class ReactionsAddRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `reactions:write`
     */
    private String token;

    /**
     * Reaction (emoji) name.
     */
    private String name;

    /**
     * File to add reaction to.
     */
    @Deprecated
    private String file;

    /**
     * File comment to add reaction to.
     */
    @Deprecated
    private String fileComment;

    /**
     * Channel where the message to add reaction to was posted.
     */
    private String channel;

    /**
     * Timestamp of the message to add reaction to.
     */
    private String timestamp;

}