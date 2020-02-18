package com.slack.api.lightning.service.builtin.oauth.default_impl;

import com.slack.api.lightning.service.builtin.oauth.OAuthAccessErrorHandler;
import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class OAuthDefaultAccessErrorHandler implements OAuthAccessErrorHandler {

    @Override
    public Response handle(OAuthCallbackRequest request, Response response, OAuthAccessResponse apiResponse) {
        log.error("Failed to run an oauth.access API call: {} - {}", apiResponse.getError(), apiResponse);

        response.setStatusCode(302);
        response.getHeaders().put("Location", Arrays.asList(request.getContext().getOauthCancellationUrl()));
        return response;
    }

}
