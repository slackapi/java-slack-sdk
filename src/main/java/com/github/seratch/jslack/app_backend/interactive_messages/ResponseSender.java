package com.github.seratch.jslack.app_backend.interactive_messages;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import com.github.seratch.jslack.app_backend.interactive_messages.response.ActionResponse;
import com.github.seratch.jslack.common.http.SlackHttpClient;
import okhttp3.Response;

import java.io.IOException;

public class ResponseSender {

    private final Slack slack;

    public ResponseSender(Slack slack) {
        this.slack = slack;
    }

    public WebhookResponse send(String responseUrl, ActionResponse response) throws IOException {
        Response httpResponse = slack.getHttpClient().postJsonPostRequest(responseUrl, response);
        String body = httpResponse.body().string();
        SlackHttpClient.debugLog(httpResponse, body);

        return WebhookResponse.builder()
                .code(httpResponse.code())
                .message(httpResponse.message())
                .body(body)
                .build();
    }
}
