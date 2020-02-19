package com.slack.api.bolt.service.builtin.oauth;

import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;

/**
 * A handler for callback request with error parameter from Slack.
 */
@FunctionalInterface
public interface OAuthErrorHandler {

    Response handle(OAuthCallbackRequest request, Response response);

}
