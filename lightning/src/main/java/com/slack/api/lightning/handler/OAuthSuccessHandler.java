package com.slack.api.lightning.handler;

import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;

@FunctionalInterface
public interface OAuthSuccessHandler {

    Response handle(OAuthCallbackRequest req, OAuthAccessResponse oauthAccess);

}
