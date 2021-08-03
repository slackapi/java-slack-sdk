package com.slack.api.bolt.service.builtin.oauth;

import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;

/**
 * An error handler for errors with `openid.connect.*` API calls.
 */
@FunctionalInterface
public interface OpenIDConnectErrorHandler {

    Response handle(OAuthCallbackRequest request, Response response, OpenIDConnectTokenResponse apiResponse);

}
