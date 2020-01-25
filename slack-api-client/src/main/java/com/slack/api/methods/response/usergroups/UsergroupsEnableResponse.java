package com.slack.api.methods.response.usergroups;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.Usergroup;
import lombok.Data;

@Data
public class UsergroupsEnableResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private Usergroup usergroup;
}