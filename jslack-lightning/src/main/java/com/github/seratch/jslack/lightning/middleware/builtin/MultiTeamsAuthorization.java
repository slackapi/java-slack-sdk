package com.github.seratch.jslack.lightning.middleware.builtin;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.response.auth.AuthTestResponse;
import com.github.seratch.jslack.lightning.AppConfig;
import com.github.seratch.jslack.lightning.context.Context;
import com.github.seratch.jslack.lightning.middleware.Middleware;
import com.github.seratch.jslack.lightning.middleware.MiddlewareChain;
import com.github.seratch.jslack.lightning.model.Bot;
import com.github.seratch.jslack.lightning.model.Installer;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.response.Response;
import com.github.seratch.jslack.lightning.service.InstallationService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.github.seratch.jslack.lightning.middleware.MiddlewareOps.isNoSlackSignatureRequest;

@Slf4j
public class MultiTeamsAuthorization implements Middleware {

    private final InstallationService installationService;

    private boolean alwaysRequestUserTokenNeeded;

    public boolean isAlwaysRequestUserTokenNeeded() {
        return alwaysRequestUserTokenNeeded;
    }

    public void setAlwaysRequestUserTokenNeeded(boolean alwaysRequestUserTokenNeeded) {
        this.alwaysRequestUserTokenNeeded = alwaysRequestUserTokenNeeded;
    }

    public MultiTeamsAuthorization(AppConfig config, InstallationService installationService) {
        setAlwaysRequestUserTokenNeeded(config.isAlwaysRequestUserTokenNeeded());
        this.installationService = installationService;
    }

    @Override
    public Response apply(Request req, Response resp, MiddlewareChain chain) throws Exception {
        if (isNoSlackSignatureRequest(req.getRequestType())) {
            return chain.next(req);
        }
        Context context = req.getContext();

        String botToken = null;
        String userToken = null;

        Bot bot = installationService.findBot(context.getEnterpriseId(), context.getTeamId());
        Installer installer = null;
        if (bot != null) {
            botToken = bot.getBotAccessToken();
        }

        if ((isAlwaysRequestUserTokenNeeded() || bot == null) && context.getRequestUserId() != null) {
            // no bot for this app - try to fetch installer's access token instead
            installer = installationService.findInstaller(
                    context.getEnterpriseId(),
                    context.getTeamId(),
                    context.getRequestUserId()
            );
            if (installer != null) {
                userToken = installer.getInstallerUserAccessToken();
            }
        }

        if (botToken == null && userToken == null) {
            return buildError(401, null, null, null);
        }

        try {
            String token = botToken != null ? botToken : userToken;
            AuthTestResponse authTestResponse = context.client().authTest(r -> r.token(token));
            if (authTestResponse.isOk()) {
                context.setBotToken(botToken);
                context.setRequestUserToken(userToken);
                context.setTeamId(authTestResponse.getTeamId());
                context.setEnterpriseId(authTestResponse.getEnterpriseId());
                if (bot != null) {
                    context.setBotId(bot.getBotId());
                    context.setBotUserId(authTestResponse.getUserId());
                }
                return chain.next(req);
            } else {
                return handleAuthTestError(authTestResponse.getError(), bot, installer, authTestResponse);
            }
        } catch (IOException e) {
            return buildError(503, null, e, null);
        } catch (SlackApiException e) {
            return buildError(503, null, null, e);
        }
    }

    protected Response handleAuthTestError(
            String errorCode,
            Bot foundBot,
            Installer foundInstaller,
            AuthTestResponse authTestResponse) throws Exception {
        if (errorCode.equals("account_inactive")) {
            // this is not recoverable - going to remove the data not to repeat the same error
            if (foundBot != null) {
                installationService.deleteBot(foundBot);
            } else if (foundInstaller != null) {
                installationService.deleteInstaller(foundInstaller);
            }
        }
        return buildError(401, authTestResponse, null, null);
    }

    protected Response buildError(
            int statusCode,
            AuthTestResponse authTestResponse,
            IOException ioException,
            SlackApiException slackException) {

        log.info("auth.test result: {}, io error: {}, api error: {}", authTestResponse, ioException, slackException);

        return Response.builder()
                .statusCode(statusCode)
                .contentType(Response.CONTENT_TYPE_APPLICATION_JSON)
                .body("{\"error\":\"a request for an unknown workspace detected\"}")
                .build();
    }

}
