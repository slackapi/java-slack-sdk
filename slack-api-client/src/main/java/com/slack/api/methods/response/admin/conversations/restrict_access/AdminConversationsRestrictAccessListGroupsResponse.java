package com.slack.api.methods.response.admin.conversations.restrict_access;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class AdminConversationsRestrictAccessListGroupsResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<String> groupIds;
    private ErrorResponseMetadata responseMetadata;
}