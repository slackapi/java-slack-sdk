package com.slack.api.methods.response.users.profile;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.User;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UsersProfileGetResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private User.Profile profile;
}