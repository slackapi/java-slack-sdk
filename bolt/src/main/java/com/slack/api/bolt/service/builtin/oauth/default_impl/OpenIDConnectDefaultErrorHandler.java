package com.slack.api.bolt.service.builtin.oauth.default_impl;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.builtin.oauth.OAuthV2AccessErrorHandler;
import com.slack.api.bolt.service.builtin.oauth.OpenIDConnectErrorHandler;
import com.slack.api.bolt.service.builtin.oauth.view.OAuthRedirectUriPageRenderer;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class OpenIDConnectDefaultErrorHandler implements OpenIDConnectErrorHandler {

    private final AppConfig appConfig;
    private final OAuthRedirectUriPageRenderer pageRenderer;

    public OpenIDConnectDefaultErrorHandler(AppConfig appConfig) {
        this.appConfig = appConfig;
        this.pageRenderer = appConfig.getOAuthRedirectUriPageRenderer();
    }

    @Override
    public Response handle(OAuthCallbackRequest req, Response response, OpenIDConnectTokenResponse apiResponse) {
        log.error("Failed to run an openid.connect.token API call: {} - {}", apiResponse.getError(), apiResponse);

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
