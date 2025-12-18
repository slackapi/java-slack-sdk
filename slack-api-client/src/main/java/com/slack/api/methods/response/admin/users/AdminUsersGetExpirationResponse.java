package com.slack.api.methods.response.admin.users;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ErrorResponseMetadata;
import com.slack.api.model.User;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminUsersGetExpirationResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private ErrorResponseMetadata responseMetadata;
    private User user;
}

