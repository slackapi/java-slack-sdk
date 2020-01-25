package com.slack.api.methods.response.chat;

import com.slack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class ChatDeleteResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String channel;
    private String ts;
}