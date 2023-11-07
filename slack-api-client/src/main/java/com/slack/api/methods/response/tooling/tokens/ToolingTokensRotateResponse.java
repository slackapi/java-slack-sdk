package com.slack.api.methods.response.tooling.tokens;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ToolingTokensRotateResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String token;
    private String refreshToken;
    private String teamId;
    private String userId;
    private Integer iat;
    private Integer exp;
    private ResponseMetadata responseMetadata;
}