package com.slack.api.methods.response.oauth;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * https://docs.slack.dev/reference/methods/oauth.v2.user.access
 */
@Data
public class OAuthV2UserAccessResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String accessToken;
    private String tokenType; // "user"
    private String idToken;
    private AuthedUser authedUser;
    private Team team;

    @Data
    public static class AuthedUser {
        private String id;
        private String scope;
    }

    @Data
    public static class Team {
        private String id;
    }
}
