package com.slack.api.bolt.service.builtin.oauth.default_impl;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.builtin.oauth.OAuthStateErrorHandler;
import com.slack.api.bolt.service.builtin.oauth.view.OAuthRedirectUriPageRenderer;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class OAuthDefaultStateErrorHandler implements OAuthStateErrorHandler {

    private final AppConfig appConfig;
    private final OAuthRedirectUriPageRenderer pageRenderer;

    public OAuthDefaultStateErrorHandler(AppConfig appConfig) {
        this.appConfig = appConfig;
        this.pageRenderer = appConfig.getOAuthRedirectUriPageRenderer();
    }

    @Override
    public Response handle(OAuthCallbackRequest request, Response response) {
        log.warn("Invalid state parameter detected - payload: {}", request.getPayload());

        String url = request.getContext().getOauthCancellationUrl();
        if (url != null && !url.isEmpty()) {
            response.setStatusCode(302);
            response.getHeaders().put("Location", Arrays.asList(request.getContext().getOauthCancellationUrl()));
        } else {
            String reason = "invalid_state";
            response.setStatusCode(200);
            response.setBody(pageRenderer.renderFailurePage(appConfig.getOauthInstallRequestURI(), reason));
            response.setContentType("text/html; charset=utf-8");
        }
        return response;
    }

}
