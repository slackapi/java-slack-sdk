package com.github.seratch.jslack.lightning.handler;

import com.slack.api.methods.response.oauth.OAuthAccessResponse;
import com.github.seratch.jslack.lightning.request.builtin.OAuthCallbackRequest;
import com.github.seratch.jslack.lightning.response.Response;

@FunctionalInterface
public interface OAuthSuccessHandler {

    Response handle(OAuthCallbackRequest req, OAuthAccessResponse oauthAccess);

}
