package com.github.seratch.jslack.api.methods.request.pins;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PinsRemoveRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `pins:write`
     */
    private String token;

    /**
     * Channel where the item is pinned to.
     */
    private String channel;

    /**
     * File to un-pin.
     */
    private String file;

    /**
     * File comment to un-pin.
     */
    private String fileComment;

    /**
     * Timestamp of the message to un-pin.
     */
    private String timestamp;

}