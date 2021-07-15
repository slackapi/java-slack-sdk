package com.slack.api.token_rotation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentToken {
    private String accessToken;
    private String refreshToken;
    private long expiresAt; // Unix time in millis
}
