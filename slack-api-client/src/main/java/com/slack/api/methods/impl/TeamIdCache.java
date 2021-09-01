package com.slack.api.methods.impl;

import com.slack.api.methods.Methods;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.auth.AuthTestRequest;
import com.slack.api.methods.response.auth.AuthTestResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

import static com.slack.api.methods.RequestFormBuilder.toForm;

@Slf4j
public class TeamIdCache {

    private static final ConcurrentMap<String, String> TOKEN_TO_TEAM_ID = new ConcurrentHashMap<>();

    private final MethodsClientImpl methodsImpl;

    public TeamIdCache(MethodsClientImpl methodsImpl) {
        this.methodsImpl = methodsImpl;
    }

    public String lookupOrResolve(String token) {
        return lookupOrResolve(token, (newToken) -> {
            try {
                FormBody.Builder form = toForm(AuthTestRequest.builder().token(newToken).build());
                Response response = methodsImpl.runPostFormWithToken(form, Methods.AUTH_TEST, token);
                AuthTestResponse authTest = methodsImpl.parseJsonResponseAndRunListeners(null, null, response, AuthTestResponse.class);
                if (authTest != null && authTest.isOk()) {
                    if (log.isDebugEnabled()) {
                        log.debug("Created cache for an auth.test API call (token: {}, team_id: {})",
                                token.substring(0, 16) + "...", authTest.getTeamId());
                    }
                    // for org admin user's token, this value can be an enterprise_id
                    return authTest.getTeamId();
                } else {
                    String error = authTest != null ? authTest.getError() : "";
                    log.error("Got an unsuccessful response from auth.test API (error: {})", error);
                }
            } catch (IOException | SlackApiException e) {
                log.error("Failed to call auth.test API (error: {})", e.getMessage(), e);
            }
            return null;
        });
    }

    private static String lookupOrResolve(String token, Function<String, String> compute) {
        if (token == null) {
            return null;
        } else {
            return TOKEN_TO_TEAM_ID.computeIfAbsent(token, compute);
        }
    }

}