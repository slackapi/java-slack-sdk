package com.slack.api.model;

import lombok.Data;

@Data
public class AppCredentials {
    private String clientId;
    private String clientSecret;
    private String verificationToken;
    private String signingSecret;
}
