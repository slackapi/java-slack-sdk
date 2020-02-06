package com.slack.api.lightning.handler;

import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;

@FunctionalInterface
public interface OAuthV2SuccessHandler {

    Response handle(OAuthCallbackRequest request, Response response, OAuthV2AccessResponse oauthAccess);

}
