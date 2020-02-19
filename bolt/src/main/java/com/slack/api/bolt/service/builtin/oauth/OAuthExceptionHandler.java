package com.slack.api.bolt.service.builtin.oauth;

import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;

/**
 *
 */
@FunctionalInterface
public interface OAuthExceptionHandler {

    Response handle(OAuthCallbackRequest request, Response response, Exception e);

}
