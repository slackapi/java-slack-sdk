package com.slack.api.methods.response.admin.teams;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

@Data
public class AdminTeamsCreateResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String team; // created team id

    private ErrorResponseMetadata responseMetadata;
}