package com.slack.api.app_backend;

import com.slack.api.RequestConfigurator;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.interactive_components.ActionResponseSender;
import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.app_backend.slash_commands.SlashCommandResponseSender;
import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.webhook.WebhookResponse;

import java.io.IOException;

/**
 * Request sender using response_url.
 */
public class ResponseSender {

    private final Slack slack;
    private final String responseUrl;
    private final ActionResponseSender actionResponseSender;
    private final SlashCommandResponseSender slashCommandResponseSender;

    public ResponseSender(String responseUrl) {
        this(Slack.getInstance(), responseUrl);
    }

    public ResponseSender(SlackConfig config, String responseUrl) {
        this(Slack.getInstance(config), responseUrl);
    }

    public ResponseSender(Slack slack, String responseUrl) {
        this.slack = slack;
        this.responseUrl = responseUrl;
        this.actionResponseSender = new ActionResponseSender(slack);
        this.slashCommandResponseSender = new SlashCommandResponseSender(slack);
    }

    public WebhookResponse sendToAction(RequestConfigurator<ActionResponse.ActionResponseBuilder> body) throws IOException {
        return sendToAction(body.configure(ActionResponse.builder()).build());
    }

    public WebhookResponse sendToAction(ActionResponse body) throws IOException {
        return actionResponseSender.send(this.responseUrl, body);
    }

    public WebhookResponse sendToCommand(RequestConfigurator<SlashCommandResponse.SlashCommandResponseBuilder> body) throws IOException {
        return sendToCommand(body.configure(SlashCommandResponse.builder()).build());
    }

    public WebhookResponse sendToCommand(SlashCommandResponse body) throws IOException {
        return slashCommandResponseSender.send(this.responseUrl, body);
    }

}
