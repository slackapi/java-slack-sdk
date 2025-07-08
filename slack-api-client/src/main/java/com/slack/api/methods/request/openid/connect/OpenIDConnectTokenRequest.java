package com.slack.api.methods.request.openid.connect;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * - https://docs.slack.dev/reference/methods/openid.connect.token
 * - https://docs.slack.dev/authentication/sign-in-with-slack
 */
@Data
@Builder
public class OpenIDConnectTokenRequest implements SlackApiRequest {

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