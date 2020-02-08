package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.context.builtin.OAuthCallbackContext;
import com.slack.api.lightning.handler.OAuthSuccessHandler;
import com.slack.api.lightning.model.Installer;
import com.slack.api.lightning.model.builtin.DefaultInstaller;
import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;
import com.slack.api.lightning.service.InstallationService;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class OAuthDefaultSuccessHandler implements OAuthSuccessHandler {

    private InstallationService installationService;

    public OAuthDefaultSuccessHandler(InstallationService installationService) {
        this.installationService = installationService;
    }

    @Override
    public Response handle(OAuthCallbackRequest request, Response response, OAuthAccessResponse o) {
        OAuthCallbackContext context = request.getContext();
        context.setEnterpriseId(o.getEnterpriseId());
        context.setTeamId(o.getTeamId());
        context.setBotUserId(o.getBot().getBotUserId());
        context.setBotToken(o.getBot().getBotAccessToken());
        context.setRequestUserId(o.getUserId());
        context.setRequestUserToken(o.getAccessToken());

        DefaultInstaller.DefaultInstallerBuilder i = DefaultInstaller.builder()
                .enterpriseId(o.getEnterpriseId())
                .teamId(o.getTeamId())
                .teamName(o.getTeamName())
                .installerUserId(o.getUserId())
                .installerUserAccessToken(o.getAccessToken())
                .scope(o.getScope())
                .installedAt(System.currentTimeMillis());

        if (o.getIncomingWebhook() != null) {
            i = i.incomingWebhookChannelId(o.getIncomingWebhook().getChannelId())
                    .incomingWebhookUrl(o.getIncomingWebhook().getUrl())
                    .incomingWebhookConfigurationUrl(o.getIncomingWebhook().getConfigurationUrl());
        }

        if (o.getBot() != null) {
            i = i.botUserId(o.getBot().getBotUserId());
            i = i.botAccessToken(o.getBot().getBotAccessToken());
            try {
                AuthTestResponse authTest = context.client().authTest(r -> r);
                if (authTest.isOk()) {
                    i = i.botId(authTest.getBotId());
                } else {
                    log.warn("Failed to call auth.test to fetch botId for the user: {} - {}", o.getBot().getBotUserId(), authTest.getError());
                }
            } catch (SlackApiException | IOException e) {
                log.warn("Failed to call auth.test to fetch botId for the user: {}", o.getBot().getBotUserId(), e);
            }
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
