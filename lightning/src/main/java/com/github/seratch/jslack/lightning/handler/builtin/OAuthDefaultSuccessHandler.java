package com.github.seratch.jslack.lightning.handler.builtin;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.response.auth.AuthTestResponse;
import com.github.seratch.jslack.api.methods.response.oauth.OAuthAccessResponse;
import com.github.seratch.jslack.lightning.context.builtin.OAuthCallbackContext;
import com.github.seratch.jslack.lightning.handler.OAuthSuccessHandler;
import com.github.seratch.jslack.lightning.model.Installer;
import com.github.seratch.jslack.lightning.model.builtin.DefaultInstaller;
import com.github.seratch.jslack.lightning.request.builtin.OAuthCallbackRequest;
import com.github.seratch.jslack.lightning.response.Response;
import com.github.seratch.jslack.lightning.service.InstallationService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class OAuthDefaultSuccessHandler implements OAuthSuccessHandler {

    private InstallationService installationService;

    public OAuthDefaultSuccessHandler(InstallationService installationService) {
        this.installationService = installationService;
    }

    @Override
    public Response handle(OAuthCallbackRequest req, OAuthAccessResponse o) {
        OAuthCallbackContext context = req.getContext();
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

        Map<String, String> headers = new HashMap<>();
        try {
            installationService.saveInstallerAndBot(installer);
            headers.put("Location", context.getOauthCompletionUrl());
        } catch (Exception e) {
            log.warn("Failed to store the installation - {}", e.getMessage(), e);
            headers.put("Location", context.getOauthCancellationUrl());
        }
        return Response.builder().statusCode(302).headers(headers).build();
    }

}
