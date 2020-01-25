package com.github.seratch.jslack.api.methods.response.admin.users;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

@Data
public class AdminUsersAssignResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private ErrorResponseMetadata responseMetadata;
}