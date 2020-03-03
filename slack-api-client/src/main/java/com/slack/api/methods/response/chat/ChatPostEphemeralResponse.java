package com.slack.api.methods.response.chat;

import com.slack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class ChatPostEphemeralResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private String deprecatedArgument;

    private String messageTs;

    // As of Jan 2020, this field is no longer available
    @Deprecated
    private String channel;
}