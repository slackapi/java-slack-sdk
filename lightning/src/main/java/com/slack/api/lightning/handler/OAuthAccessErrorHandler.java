package com.slack.api.lightning.handler;

import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;

@FunctionalInterface
public interface OAuthAccessErrorHandler {

    Response handle(OAuthCallbackRequest request, Response response, OAuthAccessResponse apiResponse);

}
