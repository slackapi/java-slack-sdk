package com.slack.api.bolt.response;

import com.slack.api.RequestConfigurator;
import com.slack.api.Slack;
import com.slack.api.app_backend.interactive_components.ActionResponseSender;
import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.app_backend.slash_commands.SlashCommandResponseSender;
import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.util.http.SlackHttpClient;
import com.slack.api.webhook.WebhookResponse;
import okhttp3.Response;

import java.io.IOException;

/**
 * HTTP response sender using response_url.
 */
public class Responder {

    private final Slack slack;
    private final String responseUrl;

    private final ActionResponseSender actionResponseSender;
    private final SlashCommandResponseSender slashCommandResponseSender;

    /**
     * Initializes with a valid response_url
     *
     * @param slack       the underlying sender
     * @param responseUrl the response_url in a payload
     */
    public Responder(Slack slack, String responseUrl) {
        this.slack = slack;
        this.responseUrl = responseUrl;
        this.actionResponseSender = new ActionResponseSender(slack);
        this.slashCommandResponseSender = new SlashCommandResponseSender(slack);
    }

    /**
     * Sends an HTTP response for a slash command invocation.
     */
    public WebhookResponse send(SlashCommandResponse response) throws IOException {
        return new SlashCommandResponseSender(slack).send(responseUrl, response);
    }

    /**
     * Sends an HTTP response for an action.
     */
    public WebhookResponse send(ActionResponse response) throws IOException {
        return new ActionResponseSender(slack).send(responseUrl, response);
    }

    /**
     * Sends an HTTP response for an outgoing webhook.
     */
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

    public WebhookResponse sendToAction(RequestConfigurator<ActionResponse.ActionResponseBuilder> body) throws IOException {
        return send(body.configure(ActionResponse.builder()).build());
    }

    public WebhookResponse sendToCommand(RequestConfigurator<SlashCommandResponse.SlashCommandResponseBuilder> body) throws IOException {
        return send(body.configure(SlashCommandResponse.builder()).build());
    }

}
