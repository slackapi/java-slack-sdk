package com.slack.api.methods.response.chat;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

@Data
public class ChatDeleteScheduledMessageResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
}