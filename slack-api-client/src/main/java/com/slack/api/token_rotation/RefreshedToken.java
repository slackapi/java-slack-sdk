package com.slack.api.token_rotation;

import lombok.Data;

@Data
public class RefreshedToken {
    private String accessToken;
    private String refreshToken;
    private long expiresAt; // Unix time in millis
}
