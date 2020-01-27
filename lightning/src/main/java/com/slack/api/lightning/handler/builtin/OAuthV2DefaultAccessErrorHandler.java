package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.handler.OAuthV2AccessErrorHandler;
import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class OAuthV2DefaultAccessErrorHandler implements OAuthV2AccessErrorHandler {

    @Override
    public Response handle(OAuthCallbackRequest req, OAuthV2AccessResponse apiResponse) {
        log.error("Failed to run an oauth.v2.access API call: {} - {}", apiResponse.getError(), apiResponse);
        Map<String, String> headers = new HashMap<>();
        headers.put("Location", req.getContext().getOauthCancellationUrl());
        return Response.builder().statusCode(302).headers(headers).build();
    }

}
