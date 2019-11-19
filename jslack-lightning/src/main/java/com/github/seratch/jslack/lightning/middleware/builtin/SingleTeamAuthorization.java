package com.github.seratch.jslack.lightning.middleware.builtin;

import com.github.seratch.jslack.api.methods.response.auth.AuthTestResponse;
import com.github.seratch.jslack.lightning.AppConfig;
import com.github.seratch.jslack.lightning.context.Context;
import com.github.seratch.jslack.lightning.middleware.Middleware;
import com.github.seratch.jslack.lightning.middleware.MiddlewareChain;
import com.github.seratch.jslack.lightning.model.Installer;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.response.Response;
import com.github.seratch.jslack.lightning.service.InstallationService;
import lombok.extern.slf4j.Slf4j;

import static com.github.seratch.jslack.lightning.middleware.MiddlewareOps.isNoSlackSignatureRequest;

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
        if (isNoSlackSignatureRequest(req.getRequestType())) {
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
