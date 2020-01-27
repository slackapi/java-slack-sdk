package com.slack.api.methods.request.channels;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
@Builder
public class ChannelsSetTopicRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `channels:write`
     */
    private String token;

    /**
     * Channel to set the topic of
     */
    private String channel;

    /**
     * The new topic
     */
    private String topic;

}