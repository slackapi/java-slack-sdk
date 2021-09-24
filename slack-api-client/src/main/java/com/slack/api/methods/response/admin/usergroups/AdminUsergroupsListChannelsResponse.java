package com.slack.api.methods.response.admin.usergroups;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Conversation;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminUsergroupsListChannelsResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<Conversation> channels;

    private ErrorResponseMetadata responseMetadata;
}