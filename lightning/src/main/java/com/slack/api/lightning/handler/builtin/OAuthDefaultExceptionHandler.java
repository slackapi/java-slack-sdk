package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.handler.OAuthExceptionHandler;
import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class OAuthDefaultExceptionHandler implements OAuthExceptionHandler {

    @Override
    public Response handle(OAuthCallbackRequest req, Exception e) {
        log.error("Failed to run an OAuth callback operation - {}", e.getMessage(), e);
        Map<String, String> headers = new HashMap<>();
        headers.put("Location", req.getContext().getOauthCancellationUrl());
        return Response.builder().statusCode(302).headers(headers).build();
    }

}
