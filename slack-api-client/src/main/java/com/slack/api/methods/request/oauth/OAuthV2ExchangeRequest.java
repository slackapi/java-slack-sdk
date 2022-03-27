package com.slack.api.methods.request.oauth;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * - https://api.slack.com/methods/oauth.v2.exchange
 * - https://api.slack.com/authentication/rotation
 */
@Data
@Builder
public class OAuthV2ExchangeRequest implements SlackApiRequest {

    /**
     * The legacy xoxb or xoxp token being migrated to use token rotation.
     */
    private String token;

    /**
     * Issued when you created your application.
     */
    private String clientId;

    /**
     * Issued when you created your application.
     */
    private String clientSecret;

}