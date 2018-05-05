package com.github.seratch.jslack.api.methods.request.pins;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

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
    private String file;

    /**
     * File comment to pin.
     */
    private String fileComment;

    /**
     * Timestamp of the message to pin.
     */
    private String timestamp;

}