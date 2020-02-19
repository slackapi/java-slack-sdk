package com.slack.api.bolt.context.builtin;

import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.response.ResponseUrlSender;
import com.slack.api.bolt.util.BuilderConfigurator;
import com.slack.api.webhook.WebhookResponse;
import lombok.*;

import java.io.IOException;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class SlashCommandContext extends Context {

    private String triggerId;
    private String responseUrl;
    private ResponseUrlSender responseUrlSender;

    public WebhookResponse respond(SlashCommandResponse response) throws IOException {
        if (responseUrlSender == null) {
            responseUrlSender = new ResponseUrlSender(slack, responseUrl);
        }
        return responseUrlSender.send(response);
    }

    public WebhookResponse respond(
            BuilderConfigurator<SlashCommandResponse.SlashCommandResponseBuilder> builder) throws IOException {
        return respond(builder.configure(SlashCommandResponse.builder()).build());
    }

    public Response ack(SlashCommandResponse response) {
        return Response.json(200, response);
    }

    public Response ack(
            BuilderConfigurator<SlashCommandResponse.SlashCommandResponseBuilder> builder) {
        return ack(builder.configure(SlashCommandResponse.builder()).build());
    }

}
