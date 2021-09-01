package com.slack.api.bolt.service.builtin;

import com.slack.api.app_backend.config.SlackAppConfig;
import com.slack.api.app_backend.oauth.OAuthFlowOperator;
import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.OAuthCallbackService;
import com.slack.api.bolt.service.OAuthStateService;
import com.slack.api.bolt.service.builtin.oauth.*;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultOAuthCallbackService implements OAuthCallbackService {

    private final AppConfig config;
    private final OAuthFlowOperator operator;
    private final OAuthStateService stateService;
    private final OAuthSuccessHandler successHandler;
    private final OAuthV2SuccessHandler successV2Handler;
    private final OpenIDConnectSuccessHandler openIDConnectSuccessHandler;
    private final OAuthErrorHandler errorHandler;
    private final OAuthStateErrorHandler stateErrorHandler;
    private final OAuthAccessErrorHandler accessErrorHandler;
    private final OAuthV2AccessErrorHandler accessV2ErrorHandler;
    private final OpenIDConnectErrorHandler openIDConnectErrorHandler;
    private final OAuthExceptionHandler exceptionHandler;

    public DefaultOAuthCallbackService(
            AppConfig config,
            OAuthStateService stateService,
            OAuthSuccessHandler successHandler,
            OAuthV2SuccessHandler successV2Handler,
            OAuthErrorHandler errorHandler,
            OAuthStateErrorHandler stateErrorHandler,
            OAuthAccessErrorHandler accessErrorHandler,
            OAuthV2AccessErrorHandler accessV2ErrorHandler,
            OAuthExceptionHandler exceptionHandler) {
        this(
                config,
                stateService,
                successHandler,
                successV2Handler,
                errorHandler,
                stateErrorHandler,
                accessErrorHandler,
                accessV2ErrorHandler,
                exceptionHandler,
                (request, response, apiResponse) -> response,
                (request, response, apiResponse) -> response
        );
    }

    public DefaultOAuthCallbackService(
            AppConfig config,
            OAuthStateService stateService,
            OAuthSuccessHandler successHandler,
            OAuthV2SuccessHandler successV2Handler,
            OAuthErrorHandler errorHandler,
            OAuthStateErrorHandler stateErrorHandler,
            OAuthAccessErrorHandler accessErrorHandler,
            OAuthV2AccessErrorHandler accessV2ErrorHandler,
            OAuthExceptionHandler exceptionHandler,
            OpenIDConnectSuccessHandler openIDConnectSuccessHandler,
            OpenIDConnectErrorHandler openIDConnectErrorHandler
    ) {
        this.config = config;
        this.stateService = stateService;
        this.successHandler = successHandler;
        this.successV2Handler = successV2Handler;
        this.openIDConnectSuccessHandler = openIDConnectSuccessHandler;
        this.errorHandler = errorHandler;
        this.stateErrorHandler = stateErrorHandler;
        this.accessErrorHandler = accessErrorHandler;
        this.accessV2ErrorHandler = accessV2ErrorHandler;
        this.exceptionHandler = exceptionHandler;
        this.openIDConnectErrorHandler = openIDConnectErrorHandler;

        SlackAppConfig slackAppConfig = SlackAppConfig.builder()
                .clientId(config.getClientId())
                .clientSecret(config.getClientSecret())
                .redirectUri(config.getRedirectUri())
                .build();
        this.operator = new OAuthFlowOperator(config.getSlack(), slackAppConfig);
    }

    public Response handle(OAuthCallbackRequest request) {
        Response response = new Response();
        VerificationCodePayload payload = request.getPayload();
        try {
            if (payload.getError() != null) {
                return errorHandler.handle(request, response);
            }
            if (!config.isStateValidationEnabled() || stateService.isValid(request)) {
                if (config.isClassicAppPermissionsEnabled()) {
                    OAuthAccessResponse oauthAccess = operator.callOAuthAccessMethod(payload);
                    if (oauthAccess.isOk()) {
                        if (config.isStateValidationEnabled()) {
                            stateService.consume(request, response);
                        }
                        return successHandler.handle(request, response, oauthAccess);
                    } else {
                        return accessErrorHandler.handle(request, response, oauthAccess);
                    }
                } else if (config.isOpenIDConnectEnabled()) {
                    OpenIDConnectTokenResponse token = operator.callOpenIDConnectToken(payload);
                    if (token.isOk()) {
                        if (config.isStateValidationEnabled()) {
                            stateService.consume(request, response);
                        }
                        return openIDConnectSuccessHandler.handle(request, response, token);
                    } else {
                        return openIDConnectErrorHandler.handle(request, response, token);
                    }
                } else {
                    OAuthV2AccessResponse oauthAccess = operator.callOAuthV2AccessMethod(payload);
                    if (oauthAccess.isOk()) {
                        if (config.isStateValidationEnabled()) {
                            stateService.consume(request, response);
                        }
                        return successV2Handler.handle(request, response, oauthAccess);
                    } else {
                        return accessV2ErrorHandler.handle(request, response, oauthAccess);
                    }
                }
            } else {
                return stateErrorHandler.handle(request, response);
            }
        } catch (Exception e) {
            log.error("Failed to handle an OAuth request - {}", e.getMessage(), e);
            return exceptionHandler.handle(request, response, e);
        }
    }

}
