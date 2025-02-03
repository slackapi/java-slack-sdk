package com.slack.api.bolt.handler.builtin;

import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.handler.BoltEventHandler;
import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.event.TokensRevokedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class DefaultTokensRevokedEventHandler implements BoltEventHandler<TokensRevokedEvent> {

    private final InstallationService installationService;
    private final ExecutorService executorService;
    private final Logger logger = LoggerFactory.getLogger(DefaultTokensRevokedEventHandler.class);

    public static DefaultTokensRevokedEventHandler getInstance(
            InstallationService installationService,
            ExecutorService executorService) {
        return new DefaultTokensRevokedEventHandler(installationService, executorService);
    }

    public DefaultTokensRevokedEventHandler(
            InstallationService installationService,
            ExecutorService executorService) {
        this.installationService = installationService;
        this.executorService = executorService;
    }

    @Override
    public Response apply(
            EventsApiPayload<TokensRevokedEvent> payload,
            EventContext context) throws IOException, SlackApiException {
        // NOTE: this payload does not have authorizations[]
        String enterpriseId = payload.getEnterpriseId();
        String teamId = payload.getTeamId();

        this.executorService.submit(() -> {
            TokensRevokedEvent.Tokens tokens = payload.getEvent().getTokens();
            if (tokens.getOauth() != null) { // user tokens
                for (String userId : tokens.    getOauth()) {
                    Installer installer = installationService.findInstaller(enterpriseId, teamId, userId);
                    if (installer != null) {
                        try {
                            installationService.deleteInstaller(installer);
                        } catch (Exception e) {
                            logger.error(
                                    "Failed to delete an installation - enterprise_id: {}, team_id: {}, user_id: {}",
                                    enterpriseId, teamId, userId);
                        }
                    }
                }
            }
            if (tokens.getBot() != null && tokens.getBot().size() > 0) { // bots
                // actually only one bot per app in a workspace
                Bot bot = installationService.findBot(enterpriseId, teamId);
                if (bot != null) {
                    try {
                        installationService.deleteBot(bot);
                    } catch (Exception e) {
                        logger.error(
                                "Failed to delete a bot installation - enterprise_id: {}, team_id: {}",
                                enterpriseId, teamId);
                    }
                }
            }
        });
        return context.ack();
    }
}
