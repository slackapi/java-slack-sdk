package com.slack.api.bolt.service.builtin.oauth;

import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;

/**
 * The handler to handle the OpenID Connect authorization.
 * Your app needs to have its own handler to do something meaningful (e.g., saving the auth result in database)
 */
@FunctionalInterface
public interface OpenIDConnectSuccessHandler {

    Response handle(OAuthCallbackRequest request, Response response, OpenIDConnectTokenResponse apiResponse);

}
