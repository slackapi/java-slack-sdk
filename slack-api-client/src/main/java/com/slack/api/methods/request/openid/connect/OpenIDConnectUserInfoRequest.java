package com.slack.api.methods.request.openid.connect;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * - https://docs.slack.dev/reference/methods/openid.connect.userInfo
 * - https://docs.slack.dev/authentication/sign-in-with-slack
 */
@Data
@Builder
public class OpenIDConnectUserInfoRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     * Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;
}