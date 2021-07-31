package com.slack.api.bolt.service.builtin.oauth;

import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;

@FunctionalInterface
public interface OpenIDConnectSuccessHandler {

    Response handle(OAuthCallbackRequest request, Response response, OpenIDConnectTokenResponse apiResponse);

}
