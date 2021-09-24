package com.slack.api.methods.response.chat;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ChatPostEphemeralResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String deprecatedArgument;

    private String messageTs;

    // As of Jan 2020, this field is no longer available
    @Deprecated
    private String channel;
}