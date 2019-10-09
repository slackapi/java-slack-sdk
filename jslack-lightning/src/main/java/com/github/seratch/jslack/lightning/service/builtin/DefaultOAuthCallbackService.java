package com.github.seratch.jslack.lightning.service.builtin;

import com.github.seratch.jslack.api.methods.response.oauth.OAuthAccessResponse;
import com.github.seratch.jslack.app_backend.config.SlackAppConfig;
import com.github.seratch.jslack.app_backend.oauth.OAuthFlowOperator;
import com.github.seratch.jslack.app_backend.oauth.payload.VerificationCodePayload;
import com.github.seratch.jslack.lightning.AppConfig;
import com.github.seratch.jslack.lightning.handler.*;
import com.github.seratch.jslack.lightning.request.builtin.OAuthCallbackRequest;
import com.github.seratch.jslack.lightning.response.Response;
import com.github.seratch.jslack.lightning.service.OAuthCallbackService;
import com.github.seratch.jslack.lightning.service.OAuthStateService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultOAuthCallbackService implements OAuthCallbackService {

    private final AppConfig config;
    private final OAuthFlowOperator operator;
    private final OAuthStateService stateService;
    private final OAuthSuccessHandler successHandler;
    private final OAuthErrorHandler errorHandler;
    private final OAuthStateErrorHandler stateErrorHandler;
    private final OAuthAccessErrorHandler accessErrorHandler;
    private final OAuthExceptionHandler exceptionHandler;

    public DefaultOAuthCallbackService(
            AppConfig config,
            OAuthStateService stateService,
            OAuthSuccessHandler successHandler,
            OAuthErrorHandler errorHandler,
            OAuthStateErrorHandler stateErrorHandler,
            OAuthAccessErrorHandler accessErrorHandler,
            OAuthExceptionHandler exceptionHandler) {
        this.config = config;
        this.stateService = stateService;
        this.successHandler = successHandler;
        this.errorHandler = errorHandler;
        this.stateErrorHandler = stateErrorHandler;
        this.accessErrorHandler = accessErrorHandler;
        this.exceptionHandler = exceptionHandler;

        SlackAppConfig slackAppConfig = SlackAppConfig.builder()
                .clientId(config.getClientId())
                .clientSecret(config.getClientSecret())
                .redirectUri(config.getRedirectUri())
                .build();
        this.operator = new OAuthFlowOperator(config.getSlack(), slackAppConfig);
    }

    public Response handle(OAuthCallbackRequest request) {
        VerificationCodePayload payload = request.getPayload();
        try {
            if (payload.getError() != null) {
                return errorHandler.handle(request);
            }
            if (stateService.isValid(payload.getState())) {
                OAuthAccessResponse oauthAccess = operator.callOAuthAccessMethod(payload);
                if (oauthAccess.isOk()) {
                    stateService.consume(payload.getState());
                    return successHandler.handle(request, oauthAccess);
                } else {
                    return accessErrorHandler.handle(request, oauthAccess);
                }
            } else {
                return stateErrorHandler.handle(request);
            }
        } catch (Exception e) {
            log.error("Failed to handle an OAuth request - {}", e.getMessage(), e);
            return exceptionHandler.handle(request, e);
        }
    }

}
