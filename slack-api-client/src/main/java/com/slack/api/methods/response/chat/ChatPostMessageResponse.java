package com.slack.api.methods.response.chat;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ErrorResponseMetadata;
import com.slack.api.model.Message;
import lombok.Data;

import java.util.List;

@Data
public class ChatPostMessageResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private String deprecatedArgument;

    // "error": "invalid_attachments",
    // "errors": ["invalid_keys"],
    private List<String> errors;

    private ErrorResponseMetadata responseMetadata;

    private String channel;
    private String ts;
    private Message message;
}