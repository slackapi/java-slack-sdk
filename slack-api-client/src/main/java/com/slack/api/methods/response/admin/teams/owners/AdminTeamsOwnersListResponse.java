package com.slack.api.methods.response.admin.teams.owners;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class AdminTeamsOwnersListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<String> ownerIds;
    private ResponseMetadata responseMetadata;

}