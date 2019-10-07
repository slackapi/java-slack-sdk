package com.github.seratch.jslack.lightning.middleware.builtin;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.response.auth.AuthTestResponse;
import com.github.seratch.jslack.lightning.context.Context;
import com.github.seratch.jslack.lightning.middleware.Middleware;
import com.github.seratch.jslack.lightning.middleware.MiddlewareChain;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.response.Response;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public abstract class MultiTeamsAuthorization implements Middleware {

    public MultiTeamsAuthorization() {
    }

    @Override
    public Response apply(Request req, Response resp, MiddlewareChain chain) throws Exception {
        Context context = req.getContext();
        AppInstallation appInstallation = findInstalledTeam(context.getEnterpriseId(), context.getTeamId());
        try {
            AuthTestResponse authResult = context.client().authTest(r -> r.token(appInstallation.getBotToken()));
            if (authResult.isOk()) {
                return chain.next(req);
            } else {
                return buildError(401, authResult, null, null);
            }
        } catch (IOException e) {
            return buildError(503, null, e, null);
        } catch (SlackApiException e) {
            return buildError(401, null, null, e);
        }
    }

    @Data
    @Builder
    public static class AppInstallation {
        private String botToken;
        private String botId; // oauth.access
        private String botUserId; // oauth.access
    }

    abstract AppInstallation findInstalledTeam(String enterpriseId, String teamId);

    protected Response buildError(
            int statusCode,
            AuthTestResponse authTestResponse,
            IOException ioException,
            SlackApiException slackException) {
        if (log.isDebugEnabled()) {
            log.debug("auth.test result: {}, io error: {}, api error: {}",
                    authTestResponse, ioException, slackException);
        }
        return Response.builder()
                .statusCode(statusCode)
                .body("{\"error\":\"a request for an unknown workspace detected\"}")
                .build();
    }

}
