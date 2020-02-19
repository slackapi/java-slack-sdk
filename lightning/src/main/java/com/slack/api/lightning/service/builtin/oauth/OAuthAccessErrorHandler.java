package com.slack.api.lightning.service.builtin.oauth;

import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;

/**
 * An error handler for errors with `oauth.access` API calls.
 */
@FunctionalInterface
public interface OAuthAccessErrorHandler {

    Response handle(OAuthCallbackRequest request, Response response, OAuthAccessResponse apiResponse);

}
