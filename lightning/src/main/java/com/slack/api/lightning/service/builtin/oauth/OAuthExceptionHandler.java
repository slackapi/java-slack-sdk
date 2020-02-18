package com.slack.api.lightning.service.builtin.oauth;

import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;

/**
 *
 */
@FunctionalInterface
public interface OAuthExceptionHandler {

    Response handle(OAuthCallbackRequest request, Response response, Exception e);

}
