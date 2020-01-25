package com.slack.api.methods.response.channels;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.Channel;
import lombok.Data;

@Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
public class ChannelsCreateResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private Channel channel;
}
