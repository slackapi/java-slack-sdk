package com.slack.api.methods.request.oauth;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/oauth.v2.completeShortTokenRotation
 */
@Data
@Builder
public class OAuthV2CompleteShortTokenRotationRequest implements SlackApiRequest {

    /**
     * Issued when you created your application. Must be the app the token being rotated was issued to.
     */
    private String clientId;

    /**
     * Issued when you created your application.
     */
    private String clientSecret;

    /**
     * The original short xoxp token being rotated (the one passed to oauth.v2.beginShortTokenRotation). Authenticates the request.
     */
    private String token;

    /**
     * The new xoxp token returned by oauth.v2.beginShortTokenRotation.
     */
    private String newToken;

}
