package com.slack.api.lightning.service.builtin.oauth;

import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;

@FunctionalInterface
public interface OAuthSuccessHandler {

    Response handle(OAuthCallbackRequest request, Response response, OAuthAccessResponse oauthAccess);

}
