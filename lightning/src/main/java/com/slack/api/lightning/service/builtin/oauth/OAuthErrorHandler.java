package com.slack.api.lightning.service.builtin.oauth;

import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;

/**
 * A handler for callback request with error parameter from Slack.
 */
@FunctionalInterface
public interface OAuthErrorHandler {

    Response handle(OAuthCallbackRequest request, Response response);

}
