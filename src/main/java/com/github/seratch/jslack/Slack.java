package com.github.seratch.jslack;

import com.github.seratch.jslack.http.SlackHttpClient;
import com.github.seratch.jslack.rtm.RTMClient;
import com.github.seratch.jslack.rtm.RTMStart;
import com.github.seratch.jslack.webhook.WebhookPayload;
import okhttp3.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Slack Integrations
 * <p>
 * https://{your team name}.slack.com/apps/manage/custom-integrations
 */
public class Slack {

    private final Optional<String> apiToken;

    /**
     * Constructor to build a simple incoming webhook client.
     */
    public Slack() {
        apiToken = Optional.empty();
    }

    /**
     * Constructor to build a bot client.
     *
     * @param apiToken api token
     */
    public Slack(String apiToken) {
        this.apiToken = Optional.of(apiToken);
    }

    // -----------------------------------------------------------------------------------------------
    // public APIs
    // -----------------------------------------------------------------------------------------------

    public Response send(String url, WebhookPayload webhookPayload) throws IOException {
        return new SlackHttpClient().postJsonPostRequest(url, webhookPayload);
    }

    public Optional<String> fetchRTMWebSocketUrl() throws IOException {
        if (apiToken.isPresent()) {
            RTMStart start = new RTMStart();
            return start.fetchWebSocketUrl(apiToken.get());
        } else {
            throw new IllegalStateException("apiToken is absent. Use constructor which accepts apiToken.");
        }
    }

    public RTMClient createRTMClient() throws IOException, URISyntaxException {
        Optional<String> wssUrl = fetchRTMWebSocketUrl();
        if (wssUrl.isPresent()) {
            return new RTMClient(fetchRTMWebSocketUrl().get());
        } else {
            throw new IllegalStateException("Couldn't fetch RTM API WebSocket endpoint. Ensure the apiToken value.");
        }
    }

}
