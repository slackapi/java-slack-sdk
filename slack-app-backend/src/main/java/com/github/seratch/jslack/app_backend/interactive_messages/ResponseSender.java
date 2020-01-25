package com.github.seratch.jslack.app_backend.interactive_messages;

import com.slack.api.Slack;
import com.slack.api.webhook.WebhookResponse;
import com.github.seratch.jslack.app_backend.interactive_messages.response.ActionResponse;
import com.slack.api.util.http.SlackHttpClient;
import okhttp3.Response;

import java.io.IOException;

@Deprecated // Use ActionResponseSender instead
public class ResponseSender {

    private final Slack slack;

    public ResponseSender(Slack slack) {
        this.slack = slack;
    }

    public WebhookResponse send(String responseUrl, ActionResponse response) throws IOException {
        SlackHttpClient httpClient = slack.getHttpClient();
        Response httpResponse = httpClient.postJsonBody(responseUrl, response);
        String body = httpResponse.body().string();
        httpClient.runHttpResponseListeners(httpResponse, body);

        return WebhookResponse.builder()
                .code(httpResponse.code())
                .message(httpResponse.message())
                .body(body)
                .build();
    }
}
