package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.handler.OAuthExceptionHandler;
import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class OAuthDefaultExceptionHandler implements OAuthExceptionHandler {

    @Override
    public Response handle(OAuthCallbackRequest request, Response response, Exception e) {
        log.error("Failed to run an OAuth callback operation - {}", e.getMessage(), e);

        response.setStatusCode(302);
        response.getHeaders().put("Location", Arrays.asList(request.getContext().getOauthCancellationUrl()));
        return response;
    }

}
