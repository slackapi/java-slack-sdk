package com.slack.api.methods.response.admin.conversations.whitelist;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

@Deprecated
@Data
public class AdminConversationsWhitelistAddResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private ResponseMetadata responseMetadata;
}