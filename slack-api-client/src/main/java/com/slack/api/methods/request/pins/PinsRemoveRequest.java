package com.slack.api.methods.request.pins;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/pins.remove
 */
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
    @Deprecated
    private String file;

    /**
     * File comment to un-pin.
     */
    @Deprecated
    private String fileComment;

    /**
     * Timestamp of the message to un-pin.
     */
    private String timestamp;

}