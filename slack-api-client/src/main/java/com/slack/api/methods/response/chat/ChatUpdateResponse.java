package com.slack.api.methods.response.chat;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Message;
import lombok.Data;

@Data
public class ChatUpdateResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private String deprecatedArgument;

    private String channel;
    private String ts;
    private String text;
    private Message message;
}