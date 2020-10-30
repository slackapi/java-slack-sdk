package com.slack.api.methods.response.usergroups.users;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;

@Data
public class UsergroupsUsersListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<String> users;
}