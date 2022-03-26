package com.slack.api.methods.request.oauth;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * - https://api.slack.com/methods/oauth.access
 * - https://api.slack.com/docs/oauth
 */
@Data
@Builder
public class OAuthAccessRequest implements SlackApiRequest {

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
     * Request the user to add your app only to a single channel.
     */
    private boolean singleChannel;

    @Override
    public String getToken() {
        return null;
    }
}