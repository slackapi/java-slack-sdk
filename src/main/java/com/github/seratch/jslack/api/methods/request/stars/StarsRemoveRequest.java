package com.github.seratch.jslack.api.methods.request.stars;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StarsRemoveRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `stars:write`
     */
    private String token;

    /**
     * File to remove star from.
     */
    private String file;

    /**
     * File comment to remove star from.
     */
    private String fileComment;

    /**
     * Channel to remove star from, or channel where the message to remove star from was posted (used with `timestamp`).
     */
    private String channel;

    /**
     * Timestamp of the message to remove star from.
     */
    private String timestamp;

}