package com.slack.api.methods.request.reactions;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/reactions.get
 */
@Data
@Builder
public class ReactionsGetRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `reactions:read`
     */
    private String token;

    /**
     * File to get reactions for.
     */
    private String file;

    /**
     * File comment to get reactions for.
     */
    private String fileComment;

    /**
     * Channel where the message to get reactions for was posted.
     */
    private String channel;

    /**
     * Timestamp of the message to get reactions for.
     */
    private String timestamp;

    /**
     * If true always return the complete reaction list.
     */
    private boolean full;

}