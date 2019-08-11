package com.github.seratch.jslack;

import com.github.seratch.jslack.api.audit.AuditClient;
import com.github.seratch.jslack.api.audit.impl.AuditClientImpl;
import com.github.seratch.jslack.api.methods.MethodsClient;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.impl.MethodsClientImpl;
import com.github.seratch.jslack.api.methods.request.rtm.RTMConnectRequest;
import com.github.seratch.jslack.api.methods.request.rtm.RTMStartRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersInfoRequest;
import com.github.seratch.jslack.api.methods.response.rtm.RTMConnectResponse;
import com.github.seratch.jslack.api.methods.response.rtm.RTMStartResponse;
import com.github.seratch.jslack.api.methods.response.users.UsersInfoResponse;
import com.github.seratch.jslack.api.model.User;
import com.github.seratch.jslack.api.rtm.RTMClient;
import com.github.seratch.jslack.api.scim.SCIMClient;
import com.github.seratch.jslack.api.scim.impl.SCIMClientImpl;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import com.github.seratch.jslack.common.http.SlackHttpClient;
import com.github.seratch.jslack.shortcut.Shortcut;
import com.github.seratch.jslack.shortcut.impl.ShortcutImpl;
import com.github.seratch.jslack.shortcut.model.ApiToken;
import okhttp3.Response;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Slack Integrations
 * <p>
 * https://{your team name}.slack.com/apps/manage/custom-integrations
 */
public class Slack {

    private static final Slack SINGLETON = new Slack(SlackConfig.DEFAULT, new SlackHttpClient());

    private final SlackHttpClient httpClient;
    private final SlackConfig config;

    public Slack() {
        this(SlackConfig.DEFAULT, new SlackHttpClient());
    }

    private Slack(SlackConfig config, SlackHttpClient httpClient) {
        this.config = config;
        this.httpClient = httpClient;
        this.httpClient.setConfig(this.config);
    }

    public static Slack getInstance() {
        return SINGLETON;
    }

    public static Slack getInstance(SlackConfig config) {
        return new Slack(config, new SlackHttpClient());
    }

    public static Slack getInstance(SlackConfig config, SlackHttpClient httpClient) {
        return new Slack(config, httpClient);
    }

    public static Slack getInstance(SlackHttpClient httpClient) {
        return new Slack(SlackConfig.DEFAULT, httpClient);
    }

    public SlackHttpClient getHttpClient() {
        return this.httpClient;
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
                .body(body)
                .build();
    }

    /**
     * Creates an RTM API client.
     *
     * @see "https://api.slack.com/docs/rate-limits#rtm"
     */
    public RTMClient rtm(String apiToken) throws IOException {
        return rtmConnect(apiToken);
    }

    /**
     * Creates an RTM API client using `/rtm.connect`.
     *
     * @see "https://api.slack.com/docs/rate-limits#rtm"
     */
    public RTMClient rtmConnect(String apiToken) throws IOException {
        return rtmConnect(apiToken, true);
    }

    /**
     * Creates an RTM API client using `/rtm.connect`.
     *
     * @see "https://api.slack.com/docs/rate-limits#rtm"
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
                throw new IllegalStateException("Failed to the RTM endpoint URL (error: " + response.getError() + ")");
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
     * @see "https://api.slack.com/docs/rate-limits#rtm"
     */
    public RTMClient rtmStart(String apiToken) throws IOException {
        return rtmStart(apiToken, true);
    }

    /**
     * Creates an RTM API client using `/rtm.start`.
     *
     * @see "https://api.slack.com/docs/rate-limits#rtm"
     */
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
                throw new IllegalStateException("Failed to the RTM endpoint URL (error: " + response.getError() + ")");
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
     * Creates a SCIM API client.
     */
    public SCIMClient scim() {
        return new SCIMClientImpl(httpClient);
    }

    public SCIMClient scim(String token) {
        return new SCIMClientImpl(httpClient, token);
    }

    /**
     * Creates a Audit Logs API client.
     */
    public AuditClient audit() {
        return new AuditClientImpl(httpClient);
    }

    public AuditClient audit(String token) {
        return new AuditClientImpl(httpClient, token);
    }

    /**
     * Creates a Methods API client.
     */
    public MethodsClient methods() {
        return new MethodsClientImpl(httpClient);
    }

    public MethodsClient methods(String token) {
        return new MethodsClientImpl(httpClient, token);
    }

    public Shortcut shortcut() {
        return new ShortcutImpl(this);
    }

    public Shortcut shortcut(ApiToken apiToken) {
        return new ShortcutImpl(this, apiToken);
    }

}
