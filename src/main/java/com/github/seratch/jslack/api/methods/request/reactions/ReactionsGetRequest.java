package com.github.seratch.jslack.api.methods.request.reactions;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

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