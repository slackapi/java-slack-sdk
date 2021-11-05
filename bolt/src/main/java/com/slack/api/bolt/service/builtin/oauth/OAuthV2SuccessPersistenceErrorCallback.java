package com.slack.api.bolt.service.builtin.oauth;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.builtin.oauth.view.OAuthRedirectUriPageRenderer;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;
import lombok.Builder;
import lombok.Data;

@FunctionalInterface
public interface OAuthV2SuccessPersistenceErrorCallback {

    @Data
    @Builder
    class Arguments {
        private Exception error;
        private AppConfig appConfig;
        private InstallationService installationService;
        private OAuthRedirectUriPageRenderer pageRenderer;

        private OAuthCallbackRequest request;
        private Response response;
        private OAuthV2AccessResponse apiResponse;
        private Installer installer;
    }

    void handle(Arguments args);

}
