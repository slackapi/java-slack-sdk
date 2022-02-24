package com.slack.api.methods.response.chat;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ErrorResponseMetadata;
import com.slack.api.model.Message;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ChatUpdateResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String deprecatedArgument;
    private ErrorResponseMetadata responseMetadata;

    private String channel;
    private String ts;
    private String text;
    private Message message;
}