package com.slack.api.methods.response.usergroups.users;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Usergroup;
import lombok.Data;

@Data
public class UsergroupsUsersUpdateResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private Usergroup usergroup;
}