package com.slack.api.methods.response.usergroups.users;

import com.slack.api.methods.SlackApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class UsergroupUsersListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<String> users;
}