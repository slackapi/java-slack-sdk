package com.github.seratch.jslack.api.methods.request.channels;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelsInfoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `channels:read`
     */
    private String token;

    /**
     * Set this to `true` to receive the locale for this channel. Defaults to `false`
     */
    private boolean includeLocale;

    /**
     * Channel to get info on
     */
    private String channel;

}