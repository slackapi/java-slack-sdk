package com.slack.api.bolt.service.builtin.oauth.default_impl;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.builtin.oauth.OAuthAccessErrorHandler;
import com.slack.api.bolt.service.builtin.oauth.view.OAuthRedirectUriPageRenderer;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class OAuthDefaultAccessErrorHandler implements OAuthAccessErrorHandler {

    private final AppConfig appConfig;
    private final OAuthRedirectUriPageRenderer pageRenderer;

    public OAuthDefaultAccessErrorHandler(AppConfig appConfig) {
        this.appConfig = appConfig;
        this.pageRenderer = appConfig.getOAuthRedirectUriPageRenderer();
    }

    @Override
    public Response handle(OAuthCallbackRequest request, Response response, OAuthAccessResponse apiResponse) {
        log.error("Failed to run an oauth.access API call: {} - {}", apiResponse.getError(), apiResponse);

        String url = request.getContext().getOauthCancellationUrl();
        if (url != null && !url.isEmpty()) {
            response.setStatusCode(302);
            response.getHeaders().put("Location", Arrays.asList(request.getContext().getOauthCancellationUrl()));
        } else {
            String reason = apiResponse.getError();
            response.setStatusCode(200);
            response.setBody(pageRenderer.renderFailurePage(appConfig.getOauthInstallRequestURI(), reason));
            response.setContentType("text/html; charset=utf-8");
        }
        return response;
    }

}
