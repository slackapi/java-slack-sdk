package com.slack.api.methods.response.admin.conversations;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

@Data
public class AdminConversationsCreateResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String channelId;
    private ErrorResponseMetadata responseMetadata;
}