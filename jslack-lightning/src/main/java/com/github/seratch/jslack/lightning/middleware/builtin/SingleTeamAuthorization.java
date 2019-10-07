package com.github.seratch.jslack.lightning.middleware.builtin;

import com.github.seratch.jslack.api.methods.response.auth.AuthTestResponse;
import com.github.seratch.jslack.lightning.AppConfig;
import com.github.seratch.jslack.lightning.context.Context;
import com.github.seratch.jslack.lightning.middleware.Middleware;
import com.github.seratch.jslack.lightning.middleware.MiddlewareChain;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.response.Response;

public class SingleTeamAuthorization implements Middleware {

    private final AppConfig appConfig;

    public SingleTeamAuthorization(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public Response apply(Request req, Response resp, MiddlewareChain chain) throws Exception {
        Context context = req.getContext();
        AuthTestResponse authResult = context.client().authTest(r -> r.token(appConfig.getSingleTeamBotToken()));
        if (authResult.isOk()) {
            if (context.getBotToken() == null) {
                context.setBotToken(appConfig.getSingleTeamBotToken());
            }
            context.setBotUserId(authResult.getUserId());
            context.setTeamId(authResult.getTeamId());
            context.setEnterpriseId(authResult.getEnterpriseId());
            return chain.next(req);
        } else {
            resp.setStatusCode(500);
            return resp;
        }
    }
}
