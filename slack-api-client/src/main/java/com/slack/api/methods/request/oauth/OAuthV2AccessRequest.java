package com.slack.api.methods.request.oauth;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * - https://api.slack.com/methods/oauth.v2.access
 * - https://api.slack.com/authentication/basics
 */
@Data
@Builder
public class OAuthV2AccessRequest implements SlackApiRequest {

    /**
     * Issued when you created your application.
     */
    private String clientId;

    /**
     * Issued when you created your application.
     */
    private String clientSecret;

    /**
     * The `code` param returned via the OAuth callback.
     */
    private String code;

    /**
     * This must match the originally submitted URI (if one was sent).
     */
    private String redirectUri;

    /**
     * The grant_type param as described in the OAuth spec.
     */
    private String grantType;

    /**
     * The refresh_token param as described in the OAuth spec.
     */
    private String refreshToken;

    @Override
    public String getToken() {
        return null;
    }
}