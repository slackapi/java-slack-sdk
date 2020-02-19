package com.slack.api.bolt.service.builtin.oauth;

import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;

@FunctionalInterface
public interface OAuthV2SuccessHandler {

    Response handle(OAuthCallbackRequest request, Response response, OAuthV2AccessResponse oauthAccess);

}
