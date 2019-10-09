package com.github.seratch.jslack.lightning.handler.builtin;

import com.github.seratch.jslack.lightning.handler.OAuthErrorHandler;
import com.github.seratch.jslack.lightning.request.builtin.OAuthCallbackRequest;
import com.github.seratch.jslack.lightning.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class OAuthDefaultErrorHandler implements OAuthErrorHandler {

    @Override
    public Response handle(OAuthCallbackRequest req) {
        log.error("Received error code in OAuth callback: {}", req.getPayload());
        Map<String, String> headers = new HashMap<>();
        headers.put("Location", req.getContext().getOauthCancellationUrl());
        return Response.builder().statusCode(302).headers(headers).build();
    }

}
