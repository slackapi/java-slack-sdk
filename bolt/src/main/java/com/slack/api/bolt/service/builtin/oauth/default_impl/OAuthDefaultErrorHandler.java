package com.slack.api.bolt.service.builtin.oauth.default_impl;

import com.slack.api.bolt.service.builtin.oauth.OAuthErrorHandler;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class OAuthDefaultErrorHandler implements OAuthErrorHandler {

    @Override
    public Response handle(OAuthCallbackRequest request, Response response) {
        log.error("Received error code in OAuth callback: {}", request.getPayload());

        response.setStatusCode(302);
        response.getHeaders().put("Location", Arrays.asList(request.getContext().getOauthCancellationUrl()));
        return response;
    }

}
