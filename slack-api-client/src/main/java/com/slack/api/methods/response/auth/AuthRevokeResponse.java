package com.slack.api.methods.response.auth;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

@Data
public class AuthRevokeResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private boolean revoked;
}