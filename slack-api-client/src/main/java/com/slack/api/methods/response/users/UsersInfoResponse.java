package com.slack.api.methods.response.users;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.User;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UsersInfoResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private User user;
}