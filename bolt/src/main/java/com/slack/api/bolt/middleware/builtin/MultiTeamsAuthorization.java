package com.slack.api.bolt.middleware.builtin;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.middleware.Middleware;
import com.slack.api.bolt.middleware.MiddlewareChain;
import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.util.Responder;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.token_rotation.RefreshedToken;
import com.slack.api.token_rotation.TokenRotator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.slack.api.bolt.middleware.MiddlewareOps.isNoAuthRequiredRequest;
import static com.slack.api.bolt.middleware.MiddlewareOps.isNoTokenRequiredRequest;
import static com.slack.api.bolt.response.ResponseTypes.ephemeral;

/**
 * Verifies if valid installations exist for requests.
 */
@Slf4j
public class MultiTeamsAuthorization implements Middleware {

    private final AppConfig config;
    private final InstallationService installationService;
    private final TokenRotator tokenRotator;

    @Data
    @AllArgsConstructor
    static class CachedAuthTestResponse {
        private AuthTestResponse response;
        private long cachedMillis;
    }

    // token -> auth.test response
    private final ConcurrentMap<String, CachedAuthTestResponse> tokenToAuthTestCache = new ConcurrentHashMap<>();
    private final Optional<ScheduledExecutorService> tokenToAuthTestCacheCleaner;

    private boolean alwaysRequestUserTokenNeeded;

    public boolean isAlwaysRequestUserTokenNeeded() {
        return alwaysRequestUserTokenNeeded;
    }

    public void setAlwaysRequestUserTokenNeeded(boolean alwaysRequestUserTokenNeeded) {
        this.alwaysRequestUserTokenNeeded = alwaysRequestUserTokenNeeded;
    }

    public MultiTeamsAuthorization(AppConfig config, InstallationService installationService) {
        this.config = config;
        this.installationService = installationService;
        this.tokenRotator = new TokenRotator(
                config.getSlack().methods(),
                config.getTokenRotationExpirationMillis(),
                config.getClientId(),
                config.getClientSecret()
        );
        setAlwaysRequestUserTokenNeeded(config.isAlwaysRequestUserTokenNeeded());
        if (config.isAuthTestCacheEnabled()) {
            boolean permanentCacheEnabled = config.getAuthTestCacheExpirationMillis() < 0;
            if (permanentCacheEnabled) {
                this.tokenToAuthTestCacheCleaner = Optional.empty();
            } else {
                this.tokenToAuthTestCacheCleaner = Optional.of(buildTokenToAuthTestCacheCleaner(() -> {
                    long expirationMillis = System.currentTimeMillis() - config.getAuthTestCacheExpirationMillis();
                    for (Map.Entry<String, CachedAuthTestResponse> each : tokenToAuthTestCache.entrySet()) {
                        if (each.getValue() == null || each.getValue().getCachedMillis() < expirationMillis) {
                            tokenToAuthTestCache.remove(each.getKey());
                        }
                    }
                }));
            }
        } else {
            this.tokenToAuthTestCacheCleaner = Optional.empty();
        }
    }

    private ScheduledExecutorService buildTokenToAuthTestCacheCleaner(Runnable task) {
        String threadGroupName = MultiTeamsAuthorization.class.getSimpleName();
        ScheduledExecutorService tokenToAuthTestCacheCleaner =
                this.config.getExecutorServiceProvider().createThreadScheduledExecutor(threadGroupName);
        tokenToAuthTestCacheCleaner.scheduleAtFixedRate(task, 120_000, 30_000, TimeUnit.MILLISECONDS);
        log.debug("The tokenToAuthTestCacheCleaner (daemon thread) started");
        return tokenToAuthTestCacheCleaner;
    }

    @Override
    protected void finalize() throws Throwable {
        if (this.tokenToAuthTestCacheCleaner.isPresent()) {
            this.tokenToAuthTestCacheCleaner.get().shutdown();
        }
        super.finalize();
    }

    @Override
    public Response apply(Request req, Response resp, MiddlewareChain chain) throws Exception {
        if (isNoAuthRequiredRequest(req.getRequestType())) {
            return chain.next(req);
        }
        if (isNoTokenRequiredRequest(req)) {
            // Nothing to do here
            // enterprise_id / team_id are already set by Request object constructor
            return chain.next(req);
        }

        Context context = req.getContext();

        String botToken = null;
        String userToken = null;

        Bot bot = installationService.findBot(context.getEnterpriseId(), context.getTeamId());
        Installer installer = null;
        if (bot != null) {
            if (bot.getBotRefreshToken() != null) {
                // A refresh token exists if token rotation is enabled
                Optional<RefreshedToken> maybeRefreshed = this.tokenRotator.performTokenRotation(r -> r
                        .accessToken(bot.getBotAccessToken())
                        .refreshToken(bot.getBotRefreshToken())
                        .expiresAt(bot.getBotTokenExpiresAt())
                );
                if (maybeRefreshed.isPresent()) {
                    RefreshedToken newOne = maybeRefreshed.get();
                    bot.setBotAccessToken(newOne.getAccessToken());
                    bot.setBotRefreshToken(newOne.getRefreshToken());
                    bot.setBotTokenExpiresAt(newOne.getExpiresAt());
                    installationService.saveBot(bot);
                }
            }
            botToken = bot.getBotAccessToken();
        }

        if ((isAlwaysRequestUserTokenNeeded() || bot == null) && context.getRequestUserId() != null) {
            // There are two patterns here:
            // 1) No bot token was found for this request -- trying to find installer's token instead
            // 2) A bot was found but this app needs to check if there is a user token
            //    which is associated with the user_id in this incoming request
            installer = installationService.findInstaller(
                    context.getEnterpriseId(),
                    context.getTeamId(),
                    context.getRequestUserId()
            );
            if (installer != null) {
                boolean refreshed = false;
                if (installer.getInstallerUserRefreshToken() != null) {
                    // A refresh token exists if token rotation is enabled
                    final Installer _i = installer;
                    Optional<RefreshedToken> maybeRefreshed = this.tokenRotator.performTokenRotation(r -> r
                            .accessToken(_i.getInstallerUserAccessToken())
                            .refreshToken(_i.getInstallerUserRefreshToken())
                            .expiresAt(_i.getInstallerUserTokenExpiresAt())
                    );
                    refreshed = refreshed || maybeRefreshed.isPresent();
                    if (maybeRefreshed.isPresent()) {
                        RefreshedToken newOne = maybeRefreshed.get();
                        installer.setInstallerUserAccessToken(newOne.getAccessToken());
                        installer.setInstallerUserRefreshToken(newOne.getRefreshToken());
                        installer.setInstallerUserTokenExpiresAt(newOne.getExpiresAt());
                    }
                }
                if (installer.getBotRefreshToken() != null) {
                    // A refresh token exists if token rotation is enabled
                    final Installer _i = installer;
                    Optional<RefreshedToken> maybeRefreshed = this.tokenRotator.performTokenRotation(r -> r
                            .accessToken(_i.getBotAccessToken())
                            .refreshToken(_i.getBotRefreshToken())
                            .expiresAt(_i.getBotTokenExpiresAt())
                    );
                    refreshed = refreshed || maybeRefreshed.isPresent();
                    if (maybeRefreshed.isPresent()) {
                        RefreshedToken newOne = maybeRefreshed.get();
                        installer.setBotAccessToken(newOne.getAccessToken());
                        installer.setBotRefreshToken(newOne.getRefreshToken());
                        installer.setBotTokenExpiresAt(newOne.getExpiresAt());
                    }
                }
                if (refreshed) {
                    // Save the refresh results for following data accesses
                    installationService.saveInstallerAndBot(installer);
                }
                userToken = installer.getInstallerUserAccessToken();
            }
        }

        if (botToken == null && userToken == null) {
            // In this case, no valid bot/user token was found for enterprise_id/team_id/user_id given by a request.
            // Bolt tries to ask the user to install the app if there is a response_url in the request.
            String responseUrl = req.getResponseUrl();
            if (responseUrl != null) {
                Responder responder = new Responder(config.getSlack(), responseUrl);
                if (req.getRequestType() != null) {
                    List<LayoutBlock> blocks = installationService.getInstallationGuideBlocks(
                            context.getEnterpriseId(), context.getTeamId(), context.getRequestUserId());
                    String text = blocks == null ? installationService.getInstallationGuideText(
                            context.getEnterpriseId(), context.getTeamId(), context.getRequestUserId()) : null;
                    if (req.getRequestType().equals(RequestType.Command)) {
                        if (blocks != null) {
                            responder.sendToCommand(body -> body.responseType(ephemeral).blocks(blocks));
                        } else {
                            responder.sendToCommand(body -> body.responseType(ephemeral).text(text));
                        }
                    } else {
                        if (blocks != null) {
                            responder.sendToAction(body -> body.responseType(ephemeral).blocks(blocks));
                        } else {
                            responder.sendToAction(body -> body.responseType(ephemeral).text(text));
                        }
                    }
                    // just for acknowledging this request
                    return Response.builder().statusCode(200).build();
                }
            } else {
                return buildError(401, null, null, null);
            }
        }

        try {
            String token = botToken != null ? botToken : userToken;
            AuthTestResponse authTestResponse = callAuthTest(token, config, context.client());
            if (authTestResponse.isOk()) {
                context.setAuthTestResponse(authTestResponse);
                context.setBotToken(botToken);
                context.setRequestUserToken(userToken);
                if (!authTestResponse.isEnterpriseInstall()) {
                    context.setTeamId(authTestResponse.getTeamId());
                    // As the team_id here is the org's ID,
                    // Request#updateContext() does this for enterprise_install
                }
                context.setEnterpriseId(authTestResponse.getEnterpriseId());
                context.setEnterpriseInstall(authTestResponse.isEnterpriseInstall());
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

    protected AuthTestResponse callAuthTest(String token, AppConfig config, MethodsClient client) throws IOException, SlackApiException {
        if (config.isAuthTestCacheEnabled()) {
            CachedAuthTestResponse cachedResponse = tokenToAuthTestCache.get(token);
            if (cachedResponse != null) {
                boolean permanentCacheEnabled = config.getAuthTestCacheExpirationMillis() < 0;
                if (permanentCacheEnabled) {
                    return cachedResponse.getResponse();
                }
                long millisToExpire = cachedResponse.getCachedMillis() + config.getAuthTestCacheExpirationMillis();
                if (millisToExpire > System.currentTimeMillis()) {
                    return cachedResponse.getResponse();
                }
            }
            AuthTestResponse response = client.authTest(r -> r.token(token));
            CachedAuthTestResponse newCache = new CachedAuthTestResponse(response, System.currentTimeMillis());
            tokenToAuthTestCache.put(token, newCache);
            return response;
        } else {
            return client.authTest(r -> r.token(token));
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
                .contentType(Response.CONTENT_TYPE_APPLICATION_JSON_UTF8)
                .body("{\"error\":\"a request for an unknown workspace detected\"}")
                .build();
    }
}
