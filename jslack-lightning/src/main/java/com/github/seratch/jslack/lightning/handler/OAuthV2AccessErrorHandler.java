package com.github.seratch.jslack.lightning.handler;

import com.github.seratch.jslack.api.methods.response.oauth.OAuthV2AccessResponse;
import com.github.seratch.jslack.lightning.request.builtin.OAuthCallbackRequest;
import com.github.seratch.jslack.lightning.response.Response;

@FunctionalInterface
public interface OAuthV2AccessErrorHandler {

    Response handle(OAuthCallbackRequest req, OAuthV2AccessResponse apiResponse);

}
