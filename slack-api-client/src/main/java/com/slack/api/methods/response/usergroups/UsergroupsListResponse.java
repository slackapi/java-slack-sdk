package com.slack.api.methods.response.usergroups;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Usergroup;
import lombok.Data;

import java.util.List;

@Data
public class UsergroupsListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Usergroup> usergroups;
}