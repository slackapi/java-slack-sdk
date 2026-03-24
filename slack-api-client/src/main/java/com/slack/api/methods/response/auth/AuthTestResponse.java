package com.slack.api.methods.response.auth;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AuthTestResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String context;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String url;
    private String team;
    private String user;
    private String botId; // only for bot tokens
    private String teamId;
    private String userId;
    private String enterpriseId;
    private String appId; // only for app-level tokens
    private String appName; // only for app-level tokens
    private boolean isEnterpriseInstall;
    private Integer expiresIn; // only for tooling tokens
}
