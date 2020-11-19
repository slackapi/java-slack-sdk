package com.slack.api.bolt.service.builtin.oauth.default_impl;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.builtin.oauth.OAuthV2AccessErrorHandler;
import com.slack.api.bolt.service.builtin.oauth.view.OAuthRedirectUriPageRenderer;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class OAuthV2DefaultAccessErrorHandler implements OAuthV2AccessErrorHandler {

    private final AppConfig appConfig;
    private final OAuthRedirectUriPageRenderer pageRenderer;

    public OAuthV2DefaultAccessErrorHandler(AppConfig appConfig) {
        this.appConfig = appConfig;
        this.pageRenderer = appConfig.getOAuthRedirectUriPageRenderer();
    }

    @Override
    public Response handle(OAuthCallbackRequest req, Response response, OAuthV2AccessResponse apiResponse) {
        log.error("Failed to run an oauth.v2.access API call: {} - {}", apiResponse.getError(), apiResponse);

        String url = req.getContext().getOauthCancellationUrl();
        if (url == null) {
            String reason = apiResponse.getError();
            response.setStatusCode(200);
            response.setBody(pageRenderer.renderFailurePage(appConfig.getOauthInstallRequestURI(), reason));
            response.setContentType("text/html; charset=utf-8");
        } else {
            response.setStatusCode(302);
            response.getHeaders().put("Location", Arrays.asList(url));
        }
        return response;
    }

}
