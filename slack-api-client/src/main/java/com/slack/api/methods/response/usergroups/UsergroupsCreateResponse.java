package com.slack.api.methods.response.usergroups;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Usergroup;
import lombok.Data;

@Data
public class UsergroupsCreateResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed; // "usergroups:write"
    private String provided; // some permissions

    private Usergroup usergroup;
}