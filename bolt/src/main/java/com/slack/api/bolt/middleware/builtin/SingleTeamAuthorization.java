package com.slack.api.bolt.middleware.builtin;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.middleware.Middleware;
import com.slack.api.bolt.middleware.MiddlewareChain;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.methods.response.auth.AuthTestResponse;
import lombok.extern.slf4j.Slf4j;

import static com.slack.api.bolt.middleware.MiddlewareOps.isNoAuthRequiredRequest;

/**
 * Verifies if the given access token when booting this app is valid for incoming requests.
 */
@Slf4j
public class SingleTeamAuthorization implements Middleware {

    private final AppConfig appConfig;
    private final InstallationService installationService;

    public SingleTeamAuthorization(AppConfig appConfig, InstallationService installationService) {
        this.appConfig = appConfig;
        this.installationService = installationService;
    }

    @Override
    public Response apply(Request req, Response resp, MiddlewareChain chain) throws Exception {
        if (isNoAuthRequiredRequest(req.getRequestType())) {
            return chain.next(req);
        }

        Context context = req.getContext();
        AuthTestResponse authResult = context.client().authTest(r -> r.token(appConfig.getSingleTeamBotToken()));
        if (authResult.isOk()) {
            if (context.getBotToken() == null) {
                context.setBotToken(appConfig.getSingleTeamBotToken());
            }
            context.setBotUserId(authResult.getUserId());
            context.setTeamId(authResult.getTeamId());
            context.setEnterpriseId(authResult.getEnterpriseId());

            if (appConfig.isAlwaysRequestUserTokenNeeded()) {
                if (installationService == null) {
                    log.warn("Skipped to fetch requestUserToken as InstallationService is not available for SingleTeamAuthorization - " +
                                    "enterprise_id: {}, team_id: {}, user_id: {}",
                            context.getEnterpriseId(), context.getTeamId(), context.getRequestUserId()
                    );
                }
                Installer installer = installationService.findInstaller(
                        context.getEnterpriseId(),
                        context.getTeamId(),
                        context.getRequestUserId()
                );
                if (installer != null) {
                    context.setRequestUserToken(installer.getInstallerUserAccessToken());
                }
            }

            return chain.next(req);
        } else {
            log.info("Invalid request detected - enterprise_id: {}, team_id: {}, user_id: {}",
                    context.getEnterpriseId(), context.getTeamId(), context.getRequestUserId());
            return Response.builder()
                    .statusCode(401)
                    .contentType(Response.CONTENT_TYPE_APPLICATION_JSON)
                    .body("{\"error\":\"a request for an unknown workspace detected\"}")
                    .build();
        }
    }
}
