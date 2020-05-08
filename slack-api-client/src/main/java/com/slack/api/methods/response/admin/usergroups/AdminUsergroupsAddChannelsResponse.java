package com.slack.api.methods.response.admin.usergroups;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class AdminUsergroupsAddChannelsResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<String> invalidChannels;

    private ErrorResponseMetadata responseMetadata;
}