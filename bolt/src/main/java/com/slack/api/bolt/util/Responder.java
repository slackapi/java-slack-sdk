package com.slack.api.bolt.util;

import com.slack.api.RequestConfigurator;
import com.slack.api.Slack;
import com.slack.api.app_backend.interactive_components.ActionResponseSender;
import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.app_backend.slash_commands.SlashCommandResponseSender;
import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.app_backend.views.InputBlockResponseSender;
import com.slack.api.app_backend.views.response.InputBlockResponse;
import com.slack.api.webhook.WebhookResponse;

import java.io.IOException;

/**
 * HTTP response sender using response_url.
 */
public class Responder {

    private final Slack slack;
    private final String responseUrl;

    private final ActionResponseSender actionResponseSender;
    private final SlashCommandResponseSender slashCommandResponseSender;
    private final InputBlockResponseSender inputBlockResponseSender;

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
        this.inputBlockResponseSender = new InputBlockResponseSender(slack);
    }

    /**
     * Sends an HTTP response for a slash command invocation.
     */
    public WebhookResponse send(SlashCommandResponse response) throws IOException {
        return slashCommandResponseSender.send(responseUrl, response);
    }

    /**
     * Sends an HTTP response for an action.
     */
    public WebhookResponse send(ActionResponse response) throws IOException {
        return actionResponseSender.send(responseUrl, response);
    }

    /**
     * Sends an HTTP response for an input block in a modal.
     */
    public WebhookResponse send(InputBlockResponse response) throws IOException {
        return inputBlockResponseSender.send(responseUrl, response);
    }

    public WebhookResponse sendToAction(RequestConfigurator<ActionResponse.ActionResponseBuilder> body) throws IOException {
        return send(body.configure(ActionResponse.builder()).build());
    }

    public WebhookResponse sendToCommand(RequestConfigurator<SlashCommandResponse.SlashCommandResponseBuilder> body) throws IOException {
        return send(body.configure(SlashCommandResponse.builder()).build());
    }

    public WebhookResponse sendFromModal(RequestConfigurator<InputBlockResponse.InputBlockResponseBuilder> body) throws IOException {
        return send(body.configure(InputBlockResponse.builder()).build());
    }

}
