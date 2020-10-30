package com.slack.api.methods.response.admin.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

import java.util.Map;

@Data
public class AdminConversationsInviteResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private Map<String, String> failedUserIds;
    private ErrorResponseMetadata responseMetadata;
}