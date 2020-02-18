package com.slack.api.lightning.service.builtin.oauth;

import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;

/**
 * An error handler for errors with `oauth.v2.access` API calls.
 */
@FunctionalInterface
public interface OAuthV2AccessErrorHandler {

    Response handle(OAuthCallbackRequest request, Response response, OAuthV2AccessResponse apiResponse);

}
