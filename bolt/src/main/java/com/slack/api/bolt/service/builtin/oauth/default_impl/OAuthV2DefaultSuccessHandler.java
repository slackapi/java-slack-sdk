package com.slack.api.bolt.service.builtin.oauth.default_impl;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.context.builtin.OAuthCallbackContext;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.model.builtin.DefaultInstaller;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.builtin.oauth.OAuthV2SuccessHandler;
import com.slack.api.bolt.service.builtin.oauth.OAuthV2SuccessPersistenceCallback;
import com.slack.api.bolt.service.builtin.oauth.OAuthV2SuccessPersistenceErrorCallback;
import com.slack.api.bolt.service.builtin.oauth.view.OAuthRedirectUriPageRenderer;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class OAuthV2DefaultSuccessHandler implements OAuthV2SuccessHandler {

    private final AppConfig appConfig;
    private final InstallationService installationService;
    private final OAuthRedirectUriPageRenderer pageRenderer;
    private OAuthV2SuccessPersistenceCallback persistenceCallback;
    private OAuthV2SuccessPersistenceErrorCallback persistenceErrorCallback;

    public OAuthV2DefaultSuccessHandler(
            AppConfig appConfig,
            InstallationService installationService) {
        this.appConfig = appConfig;
        this.installationService = installationService;
        this.pageRenderer = appConfig.getOAuthRedirectUriPageRenderer();
        this.persistenceCallback = DEFAULT_PERSISTENCE_SUCCESS_CALLBACK;
        this.persistenceErrorCallback = DEFAULT_PERSISTENCE_SUCCESS_ERROR_CALLBACK;
    }

    @Override
    public Response handle(OAuthCallbackRequest request, Response response, OAuthV2AccessResponse o) {
        OAuthCallbackContext context = request.getContext();
        // "enterprise" can be null for a workspace level installation
        String enterpriseId = o.getEnterprise() != null ? o.getEnterprise().getId() : null;
        String enterpriseName = o.getEnterprise() != null ? o.getEnterprise().getName() : null;
        // "team" can be null for org-level installation
        String teamId = o.getTeam() != null ? o.getTeam().getId() : null;
        String teamName = o.getTeam() != null ? o.getTeam().getName() : null;

        context.setEnterpriseId(enterpriseId);
        context.setTeamId(teamId);
        context.setBotUserId(o.getBotUserId());
        context.setBotToken(o.getAccessToken());
        context.setRequestUserId(o.getAuthedUser().getId());
        context.setRequestUserToken(o.getAccessToken());

        DefaultInstaller.DefaultInstallerBuilder i = DefaultInstaller.builder()
                .appId(o.getAppId())
                .botUserId(o.getBotUserId())
                .botAccessToken(o.getAccessToken())
                .botRefreshToken(o.getRefreshToken())
                .botTokenExpiresAt(o.getExpiresIn() == null ?
                        null : System.currentTimeMillis() + (o.getExpiresIn() * 1000))
                .isEnterpriseInstall(o.isEnterpriseInstall())
                .tokenType(o.getTokenType())
                .enterpriseId(enterpriseId)
                .enterpriseName(enterpriseName)
                .teamId(teamId)
                .teamName(teamName)
                .scope(o.getScope())
                .botScope(o.getScope())
                .installedAt(System.currentTimeMillis());

        if (o.getAuthedUser() != null) {
            // we can assume authed_user should exist but just in case
            i = i.installerUserId(o.getAuthedUser().getId())
                    // These properties can exist only when a user token is requested
                    .installerUserAccessToken(o.getAuthedUser().getAccessToken())
                    .installerUserScope(o.getAuthedUser().getScope())
                    // These token-rotation-related properties can be absent
                    .installerUserRefreshToken(o.getAuthedUser().getRefreshToken())
                    .installerUserTokenExpiresAt(o.getAuthedUser().getExpiresIn() == null ?
                            null : System.currentTimeMillis() + (o.getAuthedUser().getExpiresIn() * 1000));
        }

        if (o.getBotUserId() != null) {
            try {
                AuthTestResponse authTest = context.client().authTest(r -> r);
                if (authTest.isOk()) {
                    i = i.botId(authTest.getBotId());
                    if (o.isEnterpriseInstall()) {
                        i = i.enterpriseUrl(authTest.getUrl()); // https://{org domain}.enterprise.slack.com/
                    }
                } else {
                    log.warn("Failed to call auth.test to fetch botId for the user: {} - {}", o.getBotUserId(), authTest.getError());
                }
            } catch (SlackApiException | IOException e) {
                log.warn("Failed to call auth.test to fetch botId for the user: {}", o.getBotUserId(), e);
            }
        }

        if (o.getIncomingWebhook() != null) {
            i = i.incomingWebhookChannelId(o.getIncomingWebhook().getChannelId())
                    .incomingWebhookUrl(o.getIncomingWebhook().getUrl())
                    .incomingWebhookConfigurationUrl(o.getIncomingWebhook().getConfigurationUrl());
        }

        Installer installer = i.build();

        try {
            installationService.saveInstallerAndBot(installer);
            getPersistenceCallback().handle(OAuthV2SuccessPersistenceCallback.Arguments.builder()
                    .appConfig(appConfig)
                    .pageRenderer(pageRenderer)
                    .installationService(installationService)
                    .request(request)
                    .response(response)
                    .installer(installer)
                    .apiResponse(o)
                    .build());
        } catch (Exception error) {
            getPersistenceErrorCallback().handle(OAuthV2SuccessPersistenceErrorCallback.Arguments.builder()
                    .error(error)
                    .appConfig(appConfig)
                    .pageRenderer(pageRenderer)
                    .installationService(installationService)
                    .request(request)
                    .response(response)
                    .installer(installer)
                    .apiResponse(o)
                    .build());
        }
        return response;
    }

    private static final OAuthV2SuccessPersistenceCallback DEFAULT_PERSISTENCE_SUCCESS_CALLBACK = args -> {
        OAuthCallbackRequest request = args.getRequest();
        Response response = args.getResponse();
        String url = request.getContext().getOauthCompletionUrl();
        if (url != null) {
            response.setStatusCode(302);
            response.getHeaders().put("Location", Arrays.asList(url));
        } else {
            response.setStatusCode(200);
            response.setBody(args.getPageRenderer().renderSuccessPage(
                    args.getInstaller(),
                    args.getAppConfig().getOauthCompletionUrl())
            );
            response.setContentType("text/html; charset=utf-8");
        }
    };

    private static final OAuthV2SuccessPersistenceErrorCallback DEFAULT_PERSISTENCE_SUCCESS_ERROR_CALLBACK
            = args -> {
        Exception error = args.getError();
        OAuthCallbackRequest request = args.getRequest();
        Response response = args.getResponse();
        log.warn("Failed to store the installation - {}", error.getMessage(), error);
        String url = request.getContext().getOauthCancellationUrl();
        if (url != null) {
            response.setStatusCode(302);
            response.getHeaders().put("Location", Arrays.asList(url));
        } else {
            String reason = error.getMessage();
            response.setStatusCode(200);
            response.setBody(args.getPageRenderer().renderFailurePage(
                    args.getAppConfig().getOauthInstallRequestURI(), reason
            ));
            response.setContentType("text/html; charset=utf-8");
        }
    };

    public OAuthV2SuccessPersistenceCallback getPersistenceCallback() {
        return persistenceCallback;
    }

    public void setPersistenceCallback(OAuthV2SuccessPersistenceCallback persistenceCallback) {
        this.persistenceCallback = persistenceCallback;
    }

    public OAuthV2SuccessPersistenceErrorCallback getPersistenceErrorCallback() {
        return persistenceErrorCallback;
    }

    public void setPersistenceErrorCallback(OAuthV2SuccessPersistenceErrorCallback persistenceErrorCallback) {
        this.persistenceErrorCallback = persistenceErrorCallback;
    }

}
