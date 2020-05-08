package com.slack.api.methods.response.admin.usergroups;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.Conversation;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class AdminUsergroupsListChannelsResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Conversation> channels;

    private ErrorResponseMetadata responseMetadata;
}