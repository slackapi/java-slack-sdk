package com.slack.api.methods.request.channels;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Deprecated // https://docs.slack.dev/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
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