package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.handler.OAuthErrorHandler;
import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;
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
