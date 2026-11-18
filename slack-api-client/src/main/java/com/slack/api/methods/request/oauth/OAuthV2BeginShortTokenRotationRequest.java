package com.slack.api.methods.request.oauth;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/oauth.v2.beginShortTokenRotation
 */
@Data
@Builder
public class OAuthV2BeginShortTokenRotationRequest implements SlackApiRequest {

    /**
     * Issued when you created your application. Must be the app the token being rotated was issued to.
     */
    private String clientId;

    /**
     * Issued when you created your application.
     */
    private String clientSecret;

    /**
     * The xoxp token being rotated.
     */
    private String token;

}
