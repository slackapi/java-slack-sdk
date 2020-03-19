package com.slack.api.app_backend.views;

import com.slack.api.RequestConfigurator;
import com.slack.api.Slack;
import com.slack.api.app_backend.views.response.InputBlockResponse;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.util.http.SlackHttpClient;
import com.slack.api.webhook.WebhookResponse;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class InputBlockResponseSender {

    private final Slack slack;
    private final String responseUrl;

    public InputBlockResponseSender(Slack slack) {
        this(slack, null);
    }

    public InputBlockResponseSender(Slack slack, String responseUrl) {
        this.slack = slack;
        this.responseUrl = responseUrl;
    }

    public WebhookResponse send(String text) throws IOException {
        return send(InputBlockResponse.builder().text(text).build());
    }

    public WebhookResponse send(List<LayoutBlock> blocks) throws IOException {
        return send(InputBlockResponse.builder().blocks(blocks).build());
    }

    public WebhookResponse send(RequestConfigurator<InputBlockResponse.InputBlockResponseBuilder> configurator) throws IOException {
        return send(configurator.configure(InputBlockResponse.builder()).build());
    }

    public WebhookResponse send(InputBlockResponse response) throws IOException {
        if (this.responseUrl == null) {
            throw new IllegalStateException("response_url is unexpectedly absent!");
        }
        return send(responseUrl, response);
    }

    public WebhookResponse send(String responseUrl, InputBlockResponse response) throws IOException {
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
