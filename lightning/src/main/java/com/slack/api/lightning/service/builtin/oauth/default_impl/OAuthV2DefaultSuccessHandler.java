package com.slack.api.lightning.service.builtin.oauth.default_impl;

import com.slack.api.lightning.context.builtin.OAuthCallbackContext;
import com.slack.api.lightning.service.builtin.oauth.OAuthV2SuccessHandler;
import com.slack.api.lightning.model.Installer;
import com.slack.api.lightning.model.builtin.DefaultInstaller;
import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;
import com.slack.api.lightning.service.InstallationService;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class OAuthV2DefaultSuccessHandler implements OAuthV2SuccessHandler {

    private InstallationService installationService;

    public OAuthV2DefaultSuccessHandler(InstallationService installationService) {
        this.installationService = installationService;
    }

    @Override
    public Response handle(OAuthCallbackRequest request, Response response, OAuthV2AccessResponse o) {
        OAuthCallbackContext context = request.getContext();
        if (o.getEnterprise() != null) {
            context.setEnterpriseId(o.getEnterprise().getId());
        }
        context.setTeamId(o.getTeam().getId());
        context.setBotUserId(o.getBotUserId());
        context.setBotToken(o.getAccessToken());
        context.setRequestUserId(o.getAuthedUser().getId());
        context.setRequestUserToken(o.getAccessToken());

        DefaultInstaller.DefaultInstallerBuilder i = DefaultInstaller.builder()
                .botUserId(o.getBotUserId())
                .botAccessToken(o.getAccessToken())
                .enterpriseId(o.getEnterprise() != null ? o.getEnterprise().getId() : null)
                .teamId(o.getTeam().getId())
                .teamName(o.getTeam().getName())
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

        response.setStatusCode(302);
        try {
            installationService.saveInstallerAndBot(installer);
            response.getHeaders().put("Location", Arrays.asList(context.getOauthCompletionUrl()));
        } catch (Exception e) {
            log.warn("Failed to store the installation - {}", e.getMessage(), e);
            response.getHeaders().put("Location", Arrays.asList(context.getOauthCancellationUrl()));
        }
        return response;
    }
}
