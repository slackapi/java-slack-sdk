package com.slack.api.bolt.service.builtin.oauth.default_impl;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.builtin.oauth.OAuthErrorHandler;
import com.slack.api.bolt.service.builtin.oauth.view.OAuthRedirectUriPageRenderer;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class OAuthDefaultErrorHandler implements OAuthErrorHandler {

    private final AppConfig appConfig;
    private final OAuthRedirectUriPageRenderer pageRenderer;

    public OAuthDefaultErrorHandler(AppConfig appConfig) {
        this.appConfig = appConfig;
        this.pageRenderer = appConfig.getOAuthRedirectUriPageRenderer();
    }

    @Override
    public Response handle(OAuthCallbackRequest request, Response response) {
        log.error("Received error code in OAuth callback: {}", request.getPayload());

        String url = request.getContext().getOauthCancellationUrl();
        if (url != null && !url.isEmpty()) {
            response.setStatusCode(302);
            response.getHeaders().put("Location", Arrays.asList(request.getContext().getOauthCancellationUrl()));
        } else {
            String reason = request.getPayload().getError();
            response.setStatusCode(200);
            response.setBody(pageRenderer.renderFailurePage(appConfig.getOauthInstallRequestURI(), reason));
            response.setContentType("text/html; charset=utf-8");
        }
        return response;
    }

}
