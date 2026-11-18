package com.slack.api.methods.request.oauth;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/oauth.v2.user.access
 */
@Data
@Builder
public class OAuthV2UserAccessRequest implements SlackApiRequest {

    /**
     * Issued when you created your application. If possible, avoid sending `client_id` and `client_secret` as parameters in your request and instead supply the Client ID and Client Secret using the HTTP Basic authentication scheme.
     */
    private String clientId;

    /**
     * Issued when you created your application. If possible, avoid sending `client_id` and `client_secret` as parameters in your request and instead supply the Client ID and Client Secret using the HTTP Basic authentication scheme.
     */
    private String clientSecret;

    /**
     * The `code` param returned via the OAuth callback.
     */
    private String code;

    /**
     * The code_verifier param used to generate the code_challenge originally. Used for PKCE.
     */
    private String codeVerifier;

    /**
     * This must match the originally submitted URI (if one was sent).
     */
    private String redirectUri;

    /**
     * The `grant_type` param as described in the OAuth spec.
     */
    private String grantType;

    /**
     * The `refresh_token` param as described in the OAuth spec.
     */
    private String refreshToken;

    /**
     * Identity assertion JWT authorization grant.
     */
    private String assertion;

    @Override
    public String getToken() {
        return null;
    }
}
