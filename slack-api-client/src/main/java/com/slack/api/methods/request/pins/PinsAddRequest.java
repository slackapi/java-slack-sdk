package com.slack.api.methods.request.pins;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/pins.add
 */
@Data
@Builder
public class PinsAddRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `pins:write`
     */
    private String token;

    /**
     * Channel to pin the item in.
     */
    private String channel;

    /**
     * File to pin.
     */
    @Deprecated
    private String file;

    /**
     * File comment to pin.
     */
    @Deprecated
    private String fileComment;

    /**
     * Timestamp of the message to pin.
     */
    private String timestamp;

}