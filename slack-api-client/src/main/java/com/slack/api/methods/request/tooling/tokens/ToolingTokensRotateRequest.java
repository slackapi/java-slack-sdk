package com.slack.api.methods.request.tooling.tokens;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToolingTokensRotateRequest implements SlackApiRequest {
    // This token parameter is just a synonym for refreshToken
    private String token;
    private String refreshToken;
}