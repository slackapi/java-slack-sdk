package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.handler.OAuthAccessErrorHandler;
import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class OAuthDefaultAccessErrorHandler implements OAuthAccessErrorHandler {

    @Override
    public Response handle(OAuthCallbackRequest req, OAuthAccessResponse apiResponse) {
        log.error("Failed to run an oauth.access API call: {} - {}", apiResponse.getError(), apiResponse);
        Map<String, String> headers = new HashMap<>();
        headers.put("Location", req.getContext().getOauthCancellationUrl());
        return Response.builder().statusCode(302).headers(headers).build();
    }

}
