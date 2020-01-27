package com.slack.api.methods.response.auth;

import com.slack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class AuthRevokeResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private boolean revoked;
}