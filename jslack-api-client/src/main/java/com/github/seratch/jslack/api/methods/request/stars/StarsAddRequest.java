package com.github.seratch.jslack.api.methods.request.stars;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StarsAddRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `stars:write`
     */
    private String token;

    /**
     * File to add star to.
     */
    private String file;

    /**
     * File comment to add star to.
     */
    private String fileComment;

    /**
     * Channel to add star to, or channel where the message to add star to was posted (used with `timestamp`).
     */
    private String channel;

    /**
     * Timestamp of the message to add star to.
     */
    private String timestamp;

}