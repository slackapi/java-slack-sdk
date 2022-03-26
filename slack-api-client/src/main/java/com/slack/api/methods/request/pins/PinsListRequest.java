package com.slack.api.methods.request.pins;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/pins.list
 */
@Data
@Builder
public class PinsListRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `pins:read`
     */
    private String token;

    /**
     * Channel to get pinned items for.
     */
    private String channel;

}