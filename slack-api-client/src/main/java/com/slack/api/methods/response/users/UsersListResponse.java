package com.slack.api.methods.response.users;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UsersListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String offset; // user id
    private List<User> members;
    private String cacheTs;
    private ResponseMetadata responseMetadata;
}
