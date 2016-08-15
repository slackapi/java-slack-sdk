package com.github.seratch.jslack;

import com.github.seratch.jslack.shortcut.Shortcut;
import com.github.seratch.jslack.shortcut.impl.ShortcutImpl;
import com.github.seratch.jslack.shortcut.model.ApiToken;
import com.github.seratch.jslack.api.methods.MethodsClient;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.impl.MethodsClientImpl;
import com.github.seratch.jslack.api.methods.request.rtm.RTMStartRequest;
import com.github.seratch.jslack.api.rtm.RTMClient;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.common.http.SlackHttpClient;
import okhttp3.Response;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Slack Integrations
 * <p>
 * https://{your team name}.slack.com/apps/manage/custom-integrations
 */
public class Slack {

    private static final Slack SINGLETON = new Slack();

    public static Slack getInstance() {
        return SINGLETON;
    }

    /**
     * Send a data to Incoming Webhook endpoint.
     */
    public WebhookResponse send(String url, Payload payload) throws IOException {
        Response httpResponse = new SlackHttpClient().postJsonPostRequest(url, payload);
        String body = httpResponse.body().string();
        SlackHttpClient.debugLog(httpResponse, body);

        return WebhookResponse.builder()
                .code(httpResponse.code())
                .message(httpResponse.message())
                .body(body)
                .build();
    }

    /**
     * Creates an RTM API client.
     */
    public RTMClient rtm(String apiToken) throws IOException {
        try {
            return new RTMClient(methods()
                    .rtmStart(RTMStartRequest.builder().token(apiToken).build())
                    .getUrl());
        } catch (SlackApiException | URISyntaxException e) {
            throw new IllegalStateException("Couldn't fetch RTM API WebSocket endpoint. Ensure the apiToken value.");
        }
    }

    /**
     * Creates a Methods API client.
     */
    public MethodsClient methods() {
        return new MethodsClientImpl();
    }

    public Shortcut shortcut() {
        return new ShortcutImpl();
    }

    public Shortcut shortcut(ApiToken apiToken) {
        return new ShortcutImpl(apiToken);
    }

}
