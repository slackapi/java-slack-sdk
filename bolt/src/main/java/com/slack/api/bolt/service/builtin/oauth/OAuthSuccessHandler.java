package com.slack.api.bolt.service.builtin.oauth;

import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;

@FunctionalInterface
public interface OAuthSuccessHandler {

    Response handle(OAuthCallbackRequest request, Response response, OAuthAccessResponse oauthAccess);

}
