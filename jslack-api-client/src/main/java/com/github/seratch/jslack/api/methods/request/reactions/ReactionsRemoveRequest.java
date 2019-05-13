package com.github.seratch.jslack.api.methods.request.reactions;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReactionsRemoveRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `reactions:write`
     */
    private String token;

    /**
     * Reaction (emoji) name.
     */
    private String name;

    /**
     * File to remove reaction from.
     */
    private String file;

    /**
     * File comment to remove reaction from.
     */
    private String fileComment;

    /**
     * Channel where the message to remove reaction from was posted.
     */
    private String channel;

    /**
     * Timestamp of the message to remove reaction from.
     */
    private String timestamp;

}