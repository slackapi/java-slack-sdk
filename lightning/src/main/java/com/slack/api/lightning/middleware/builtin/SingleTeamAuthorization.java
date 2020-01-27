package com.slack.api.lightning.middleware.builtin;

import com.slack.api.lightning.AppConfig;
import com.slack.api.lightning.context.Context;
import com.slack.api.lightning.middleware.Middleware;
import com.slack.api.lightning.middleware.MiddlewareChain;
import com.slack.api.lightning.model.Installer;
import com.slack.api.lightning.request.Request;
import com.slack.api.lightning.response.Response;
import com.slack.api.lightning.service.InstallationService;
import com.slack.api.methods.response.auth.AuthTestResponse;
import lombok.extern.slf4j.Slf4j;

import static com.slack.api.lightning.middleware.MiddlewareOps.isNoAuthRequiredRequest;

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
