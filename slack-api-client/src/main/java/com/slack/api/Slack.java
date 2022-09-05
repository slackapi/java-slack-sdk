package com.slack.api;

import com.slack.api.audit.AsyncAuditClient;
import com.slack.api.audit.AuditClient;
import com.slack.api.audit.AuditConfig;
import com.slack.api.audit.impl.AsyncAuditClientImpl;
import com.slack.api.audit.impl.AuditClientImpl;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.MethodsConfig;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.impl.AsyncMethodsClientImpl;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.impl.TeamIdCache;
import com.slack.api.methods.request.rtm.RTMConnectRequest;
import com.slack.api.methods.request.rtm.RTMStartRequest;
import com.slack.api.methods.request.users.UsersInfoRequest;
import com.slack.api.methods.response.apps.connections.AppsConnectionsOpenResponse;
import com.slack.api.methods.response.rtm.RTMConnectResponse;
import com.slack.api.methods.response.rtm.RTMStartResponse;
import com.slack.api.methods.response.users.UsersInfoResponse;
import com.slack.api.model.User;
import com.slack.api.rate_limits.metrics.RequestStats;
import com.slack.api.rtm.RTMClient;
import com.slack.api.scim.AsyncSCIMClient;
import com.slack.api.scim.SCIMClient;
import com.slack.api.scim.SCIMConfig;
import com.slack.api.scim.impl.AsyncSCIMClientImpl;
import com.slack.api.scim.impl.SCIMClientImpl;
import com.slack.api.socket_mode.SocketModeClient;
import com.slack.api.socket_mode.impl.SocketModeClientJavaWSImpl;
import com.slack.api.socket_mode.impl.SocketModeClientTyrusImpl;
import com.slack.api.status.v1.LegacyStatusClient;
import com.slack.api.status.v1.impl.LegacyStatusClientImpl;
import com.slack.api.status.v2.StatusClient;
import com.slack.api.status.v2.impl.StatusClientImpl;
import com.slack.api.util.http.SlackHttpClient;
import com.slack.api.webhook.Payload;
import com.slack.api.webhook.WebhookResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.slack.api.util.http.SlackHttpClient.buildSlackHttpClient;

/**
 * This class is a kind of facade of a variety of Slack API clients offered by this SDK.
 * Any objects of this class and all the APIs this class provides are thread-safe.
 * We recommend sharing an instance across your application.
 * <p>
 * This class internally uses the OkHttpClient and the client has its own daemon thread
 * for realizing its optimal resource management. When your app needs to close all the threads when shutting down etc,
 * call #close() method to terminate those threads.
 * <p>
 * If your application depends on the Gson library for JSON manipulation as with this SDK
 * and the app uses the latest version for some reasons, just in case, you may need to check
 * the binary-compatibility among their releases. We don't think any issues happened before but
 * in general, some libraries may break bin-compatibility among major/minor releases.
 *
 * @see <a href="https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/">OkHttpClient</a>
 * @see <a href="https://github.com/google/gson">Gson</a>
 */
@Slf4j
public class Slack implements AutoCloseable {

    private static final Slack SINGLETON = new Slack(SlackConfig.DEFAULT, new SlackHttpClient());

    private final SlackHttpClient httpClient;
    private final SlackConfig config;

    public Slack() {
        this(SlackConfig.DEFAULT, buildSlackHttpClient(SlackConfig.DEFAULT));
    }

    private Slack(SlackConfig config) {
        this(config, buildSlackHttpClient(config));
    }

    private Slack(SlackConfig config, SlackHttpClient httpClient) {
        this.config = config;
        if (!this.config.equals(SlackConfig.DEFAULT)) {
            this.config.synchronizeExecutorServiceProviders();
        }
        this.httpClient = httpClient;
        this.httpClient.setConfig(this.config);
    }

    public static Slack getInstance() {
        return SINGLETON;
    }

    public static Slack getInstance(SlackConfig config) {
        return new Slack(config);
    }

    public static Slack getInstance(SlackConfig config, SlackHttpClient httpClient) {
        return new Slack(config, httpClient);
    }

    public static Slack getInstance(SlackHttpClient httpClient) {
        return new Slack(SlackConfig.DEFAULT, httpClient);
    }

    public SlackConfig getConfig() {
        return this.config;
    }

    public SlackHttpClient getHttpClient() {
        return this.httpClient;
    }

    @Override
    public void close() throws Exception {
        getHttpClient().close();
    }

    /**
     * Send a data to Incoming Webhook endpoint.
     */
    public WebhookResponse send(String url, Payload payload) throws IOException {
        SlackHttpClient httpClient = getHttpClient();
        Response httpResponse = httpClient.postJsonBody(url, payload);
        String body = httpResponse.body().string();
        httpClient.runHttpResponseListeners(httpResponse, body);

        return WebhookResponse.builder()
                .code(httpResponse.code())
                .message(httpResponse.message())
                .headers(httpResponse.headers().toMultimap())
                .body(body)
                .build();
    }

    /**
     * Send a raw JSON body to Incoming Webhook endpoint.
     */
    public WebhookResponse send(String url, String payload) throws IOException {
        SlackHttpClient httpClient = getHttpClient();
        Response httpResponse = httpClient.postJsonBody(url, payload);
        String body = httpResponse.body().string();
        httpClient.runHttpResponseListeners(httpResponse, body);

        return WebhookResponse.builder()
                .code(httpResponse.code())
                .message(httpResponse.message())
                .headers(httpResponse.headers().toMultimap())
                .body(body)
                .build();
    }

    public SocketModeClient socketMode(String appToken) throws IOException {
        return socketMode(appToken, SocketModeClient.Backend.Tyrus);
    }

    public SocketModeClient socketMode(String appToken, SocketModeClient.Backend backend) throws IOException {
        String url = issueSocketModeUrl(appToken);
        try {
            switch (backend) {
                case JavaWebSocket:
                    return new SocketModeClientJavaWSImpl(this, appToken, url);
                default:
                    return new SocketModeClientTyrusImpl(this, appToken, url);
            }
        } catch (URISyntaxException e) {
            String message = "Failed to connect to the Socket Mode API endpoint. (message: " + e.getMessage() + ")";
            throw new IOException(message, e);
        }
    }

    public String issueSocketModeUrl(String appToken) throws IOException {
        try {
            AppsConnectionsOpenResponse response = methods().appsConnectionsOpen(r -> r.token(appToken));
            if (response.isOk()) {
                return response.getUrl();
            } else {
                String message = "Failed to connect to the Socket Mode endpoint URL (error: " + response.getError() + ")";
                throw new IllegalStateException(message);
            }
        } catch (SlackApiException e) {
            String message = "Failed to connect to the Socket Mode API endpoint. (" +
                    "status: " + e.getResponse().code() + ", " +
                    "error: " + (e.getError() != null ? e.getError().getError() : "") +
                    ")";
            throw new IOException(message, e);
        }
    }

    /**
     * Creates an RTM API client.
     *
     * @see <a href="https://api.slack.com/rtm">Slack RTM API</a>
     * @see <a href="https://api.slack.com/docs/rate-limits#rtm">RTM's Rate Limits</a>
     */
    public RTMClient rtm(String apiToken) throws IOException {
        return rtmConnect(apiToken);
    }

    /**
     * Creates an RTM API client using `/rtm.connect`.
     *
     * @see <a href="https://api.slack.com/rtm">Slack RTM API</a>
     * @see <a href="https://api.slack.com/docs/rate-limits#rtm">RTM's Rate Limits</a>
     */
    public RTMClient rtmConnect(String apiToken) throws IOException {
        return rtmConnect(apiToken, true);
    }

    /**
     * Creates an RTM API client using `/rtm.connect`.
     *
     * @see <a href="https://api.slack.com/rtm">Slack RTM API</a>
     * @see <a href="https://api.slack.com/docs/rate-limits#rtm">RTM's Rate Limits</a>
     */
    public RTMClient rtmConnect(String apiToken, boolean fullUserInfoRequired) throws IOException {
        try {
            RTMConnectResponse response = methods().rtmConnect(RTMConnectRequest.builder().token(apiToken).build());
            if (response.isOk()) {
                User connectedBotUser = response.getSelf();
                if (fullUserInfoRequired) {
                    String userId = response.getSelf().getId();
                    UsersInfoResponse resp = this.methods().usersInfo(UsersInfoRequest.builder().token(apiToken).user(userId).build());
                    if (resp.isOk()) {
                        connectedBotUser = resp.getUser();
                    } else {
                        String errorMessage = "Failed to get fill user info (user id: " + response.getSelf().getId() + ", error: " + resp.getError() + ")";
                        throw new IllegalStateException(errorMessage);
                    }
                }
                return new RTMClient(this, apiToken, response.getUrl(), connectedBotUser);
            } else {
                throw new IllegalStateException("Failed to connect to the RTM endpoint URL (error: " + response.getError() + ")");
            }
        } catch (SlackApiException e) {
            throw new IllegalStateException(
                    "Failed to connect to the RTM API endpoint. (" +
                            "status: " + e.getResponse().code() + ", " +
                            "error: " + e.getError().getError() +
                            ")", e);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(
                    "Failed to connect to the RTM API endpoint. (message: " + e.getMessage() + ")", e);
        }
    }

    /**
     * Creates an RTM API client using `/rtm.start`.
     *
     * @see <a href="https://api.slack.com/rtm">Slack RTM API</a>
     * @see <a href="https://api.slack.com/docs/rate-limits#rtm">RTM's Rate Limits</a>
     * @deprecated Use #rtmConnect() instead
     */
    @Deprecated
    public RTMClient rtmStart(String apiToken) throws IOException {
        return rtmStart(apiToken, true);
    }

    /**
     * Creates an RTM API client using `/rtm.start`.
     *
     * @see <a href="https://api.slack.com/rtm">Slack RTM API</a>
     * @see <a href="https://api.slack.com/docs/rate-limits#rtm">RTM's Rate Limits</a>
     * @deprecated Use #rtmConnect() instead
     */
    @Deprecated
    public RTMClient rtmStart(String apiToken, boolean fullUserInfoRequired) throws IOException {
        try {
            RTMStartResponse response = methods().rtmStart(RTMStartRequest.builder().token(apiToken).build());
            if (response.isOk()) {
                User connectedBotUser = response.getSelf();
                if (fullUserInfoRequired) {
                    String userId = response.getSelf().getId();
                    UsersInfoResponse resp = this.methods().usersInfo(UsersInfoRequest.builder().token(apiToken).user(userId).build());
                    if (resp.isOk()) {
                        connectedBotUser = resp.getUser();
                    } else {
                        String errorMessage = "Failed to get fill user info (user id: " + response.getSelf().getId() + ", error: " + resp.getError() + ")";
                        throw new IllegalStateException(errorMessage);
                    }
                }
                return new RTMClient(this, apiToken, response.getUrl(), connectedBotUser);
            } else {
                throw new IllegalStateException("Failed to connect to the RTM endpoint URL (error: " + response.getError() + ")");
            }
        } catch (SlackApiException e) {
            throw new IllegalStateException(
                    "Failed to connect to the RTM API endpoint. (" +
                            "status: " + e.getResponse().code() + ", " +
                            "error: " + e.getError().getError() +
                            ")", e);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(
                    "Failed to connect to the RTM API endpoint. (message: " + e.getMessage() + ")", e);
        }
    }

    /**
     * Creates a Status API client.
     */
    public LegacyStatusClient statusLegacy() {
        LegacyStatusClientImpl client = new LegacyStatusClientImpl(httpClient);
        client.setEndpointUrlPrefix(config.getLegacyStatusEndpointUrlPrefix());
        return client;
    }

    public StatusClient status() {
        StatusClientImpl client = new StatusClientImpl(httpClient);
        client.setEndpointUrlPrefix(config.getStatusEndpointUrlPrefix());
        return client;
    }

    /**
     * Creates a SCIM API client.
     */
    public SCIMClient scim() {
        return scim(null);
    }

    public SCIMClient scim(String token) {
        MethodsClientImpl methods = new MethodsClientImpl(httpClient, token);
        methods.setEndpointUrlPrefix(config.getMethodsEndpointUrlPrefix());
        SCIMClientImpl client = new SCIMClientImpl(
                config,
                httpClient,
                new TeamIdCache(methods), // will create a cache with enterprise_id
                token);
        client.setEndpointUrlPrefix(config.getScimEndpointUrlPrefix());
        return client;
    }

    public AsyncSCIMClient scimAsync(String token) {
        MethodsClientImpl methods = new MethodsClientImpl(httpClient, token);
        methods.setEndpointUrlPrefix(config.getMethodsEndpointUrlPrefix());
        SCIMClientImpl client = new SCIMClientImpl(
                config,
                httpClient,
                new TeamIdCache(methods), // will create a cache with enterprise_id
                token);
        client.setEndpointUrlPrefix(config.getScimEndpointUrlPrefix());
        return new AsyncSCIMClientImpl(token, client, methods, config);
    }

    public RequestStats scimStats(String enterpriseId) {
        return scimStats(SCIMConfig.DEFAULT_SINGLETON_EXECUTOR_NAME, enterpriseId);
    }

    public RequestStats scimStats(String executorName, String enterpriseId) {
        return config.getSCIMConfig().getMetricsDatastore().getStats(executorName, enterpriseId);
    }

    /**
     * Creates an Audit Logs API client.
     */
    public AuditClient audit() {
        return audit(null);
    }

    public AuditClient audit(String token) {
        MethodsClientImpl methods = new MethodsClientImpl(httpClient, token);
        methods.setEndpointUrlPrefix(config.getMethodsEndpointUrlPrefix());
        AuditClientImpl audit = new AuditClientImpl(
                config,
                httpClient,
                new TeamIdCache(methods), // will create a cache with enterprise_id
                token
        );
        audit.setEndpointUrlPrefix(config.getAuditEndpointUrlPrefix());
        return audit;
    }

    public AsyncAuditClient auditAsync(String token) {
        MethodsClientImpl methods = new MethodsClientImpl(httpClient, token);
        methods.setEndpointUrlPrefix(config.getMethodsEndpointUrlPrefix());
        AuditClientImpl audit = new AuditClientImpl(
                config,
                httpClient,
                new TeamIdCache(methods), // will create a cache with enterprise_id
                token
        );
        audit.setEndpointUrlPrefix(config.getAuditEndpointUrlPrefix());
        return new AsyncAuditClientImpl(token, audit, methods, config);
    }

    public RequestStats auditStats(String enterpriseId) {
        return auditStats(AuditConfig.DEFAULT_SINGLETON_EXECUTOR_NAME, enterpriseId);
    }

    public RequestStats auditStats(String executorName, String enterpriseId) {
        return config.getAuditConfig().getMetricsDatastore().getStats(executorName, enterpriseId);
    }

    /**
     * Creates a Methods API client.
     */
    public MethodsClient methods() {
        return methods(null);
    }

    public MethodsClient methods(String token) {
        return methods(token, null);
    }

    public MethodsClient methods(String token, String teamId) {
        MethodsClientImpl client = new MethodsClientImpl(httpClient, token, teamId);
        client.setEndpointUrlPrefix(config.getMethodsEndpointUrlPrefix());
        return client;
    }

    public AsyncMethodsClient methodsAsync() {
        return methodsAsync(null, null);
    }

    public AsyncMethodsClient methodsAsync(String token) {
        return methodsAsync(token, null);
    }

    public AsyncMethodsClient methodsAsync(String token, String teamId) {
        MethodsClientImpl client = new MethodsClientImpl(httpClient, token, teamId);
        client.setEndpointUrlPrefix(config.getMethodsEndpointUrlPrefix());
        return new AsyncMethodsClientImpl(token, client, config);
    }

    public RequestStats methodsStats(String teamId) {
        return methodsStats(MethodsConfig.DEFAULT_SINGLETON_EXECUTOR_NAME, teamId);
    }

    public RequestStats methodsStats(String executorName, String teamId) {
        return config.getMethodsConfig().getMetricsDatastore().getStats(executorName, teamId);
    }

}
