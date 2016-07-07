package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.MethodsClient;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.impl.MethodsClientImpl;
import com.github.seratch.jslack.api.methods.request.rtm.RTMStartRequest;
import com.github.seratch.jslack.api.rtm.RTMClient;
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

    /**
     * Send a data to Incoming Webhook endpoint.
     */
    public Response send(String url, Payload payload) throws IOException {
        return new SlackHttpClient().postJsonPostRequest(url, payload);
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

}
