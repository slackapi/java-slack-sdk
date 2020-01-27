package com.slack.api.methods.request.channels;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
@Builder
public class ChannelsSetPurposeRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `channels:write`
     */
    private String token;

    /**
     * Channel to set the purpose of
     */
    private String channel;

    /**
     * The new purpose
     */
    private String purpose;

}