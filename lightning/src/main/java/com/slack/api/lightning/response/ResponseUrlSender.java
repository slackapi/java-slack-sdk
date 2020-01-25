package com.slack.api.lightning.response;

import com.slack.api.Slack;
import com.slack.api.app_backend.interactive_components.ActionResponseSender;
import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.app_backend.slash_commands.SlashCommandResponseSender;
import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.util.http.SlackHttpClient;
import com.slack.api.webhook.WebhookResponse;
import okhttp3.Response;

import java.io.IOException;

public class ResponseUrlSender {

    private final Slack slack;
    private final String responseUrl;

    public ResponseUrlSender(Slack slack, String responseUrl) {
        this.slack = slack;
        this.responseUrl = responseUrl;
    }

    public WebhookResponse send(com.slack.api.app_backend.outgoing_webhooks.response.WebhookResponse response) throws IOException {
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

    public WebhookResponse send(SlashCommandResponse response) throws IOException {
        return new SlashCommandResponseSender(slack).send(responseUrl, response);
    }

    public WebhookResponse send(ActionResponse response) throws IOException {
        return new ActionResponseSender(slack).send(responseUrl, response);
    }

}
