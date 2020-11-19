package com.slack.api.bolt.service.builtin.oauth.default_impl;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.context.builtin.OAuthCallbackContext;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.model.builtin.DefaultInstaller;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.builtin.oauth.OAuthV2SuccessHandler;
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

    public OAuthV2DefaultSuccessHandler(
            AppConfig appConfig,
            InstallationService installationService) {
        this.appConfig = appConfig;
        this.installationService = installationService;
        this.pageRenderer = appConfig.getOAuthRedirectUriPageRenderer();
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
                .isEnterpriseInstall(o.isEnterpriseInstall())
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
                    .installerUserAccessToken(o.getAuthedUser().getAccessToken())
                    .installerUserScope(o.getAuthedUser().getScope());
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
            String url = context.getOauthCompletionUrl();
            if (url != null) {
                response.setStatusCode(302);
                response.getHeaders().put("Location", Arrays.asList(url));
            } else {
                response.setStatusCode(200);
                response.setBody(pageRenderer.renderSuccessPage(installer, appConfig.getOauthCompletionUrl()));
                response.setContentType("text/html; charset=utf-8");
            }
        } catch (Exception e) {
            log.warn("Failed to store the installation - {}", e.getMessage(), e);
            String url = context.getOauthCancellationUrl();
            if (url != null) {
                response.setStatusCode(302);
                response.getHeaders().put("Location", Arrays.asList(url));
            } else {
                String reason = e.getMessage();
                response.setStatusCode(200);
                response.setBody(pageRenderer.renderFailurePage(appConfig.getOauthInstallRequestURI(), reason));
                response.setContentType("text/html; charset=utf-8");
            }
        }
        return response;
    }
}
