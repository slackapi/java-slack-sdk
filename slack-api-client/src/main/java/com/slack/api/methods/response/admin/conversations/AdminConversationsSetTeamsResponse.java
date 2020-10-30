package com.slack.api.methods.response.admin.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

@Data
public class AdminConversationsSetTeamsResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String channel; // channel id
    private ErrorResponseMetadata responseMetadata;
}