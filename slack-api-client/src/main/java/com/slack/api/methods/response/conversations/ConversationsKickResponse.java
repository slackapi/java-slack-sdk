package com.slack.api.methods.response.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ConversationsKickResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private Object errors; // TODO: typing
    private transient Map<String, List<String>> httpResponseHeaders;
}
