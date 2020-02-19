package com.slack.api.lightning.service.builtin.oauth;

import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;

@FunctionalInterface
public interface OAuthStateErrorHandler {

    Response handle(OAuthCallbackRequest request, Response response);

}
