package com.slack.api.methods.request.tooling.tokens;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToolingTokensRotateRequest implements SlackApiRequest {

    private String token;
    private String refreshToken;

}