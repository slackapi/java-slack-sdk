package com.slack.api.lightning.service.builtin.oauth.default_impl;

import com.slack.api.lightning.service.builtin.oauth.OAuthStateErrorHandler;
import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class OAuthDefaultStateErrorHandler implements OAuthStateErrorHandler {

    @Override
    public Response handle(OAuthCallbackRequest request, Response response) {
        log.warn("Invalid state parameter detected - payload: {}", request.getPayload());

        response.setStatusCode(302);
        response.getHeaders().put("Location", Arrays.asList(request.getContext().getOauthCancellationUrl()));
        return response;
    }

}
