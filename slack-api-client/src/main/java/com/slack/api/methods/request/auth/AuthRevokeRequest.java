package com.slack.api.methods.request.auth;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/auth.revoke
 */
@Data
@Builder
public class AuthRevokeRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `none`
     */
    private String token;

    /**
     * Setting this parameter to `1` triggers a _testing mode_ where the specified token will not actually be revoked.
     */
    private boolean test;
}