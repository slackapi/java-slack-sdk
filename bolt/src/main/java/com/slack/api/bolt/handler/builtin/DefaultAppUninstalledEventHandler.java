package com.slack.api.bolt.handler.builtin;

import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.handler.BoltEventHandler;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.event.AppUninstalledEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class DefaultAppUninstalledEventHandler implements BoltEventHandler<AppUninstalledEvent> {

    private final InstallationService installationService;
    private final ExecutorService executorService;
    private final Logger logger = LoggerFactory.getLogger(DefaultAppUninstalledEventHandler.class);

    public static DefaultAppUninstalledEventHandler getInstance(
            InstallationService installationService,
            ExecutorService executorService) {
        return new DefaultAppUninstalledEventHandler(installationService, executorService);
    }

    public DefaultAppUninstalledEventHandler(
            InstallationService installationService,
            ExecutorService executorService) {
        this.installationService = installationService;
        this.executorService = executorService;
    }

    @Override
    public Response apply(
            EventsApiPayload<AppUninstalledEvent> payload,
            EventContext context) throws IOException, SlackApiException {
        // NOTE: this payload does not have authorizations[]
        String enterpriseId = payload.getEnterpriseId();
        String teamId = payload.getTeamId();

        this.executorService.submit(() -> {
            try {
                installationService.deleteAll(enterpriseId, teamId);
                logger.info("Deleted all installation data for enterprise_id: {}, team_id: {}",
                        enterpriseId, teamId);
            } catch (Exception e) {
                logger.error("Failed to delete all installation data for enterprise_id: {}, team_id: {}",
                        enterpriseId, teamId, e);
            }
        });
        return context.ack();
    }
}
