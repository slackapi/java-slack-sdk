package com.github.seratch.jslack.lightning.context.builtin;

import com.github.seratch.jslack.api.webhook.WebhookResponse;
import com.github.seratch.jslack.app_backend.slash_commands.response.SlashCommandResponse;
import com.github.seratch.jslack.lightning.context.Context;
import com.github.seratch.jslack.lightning.response.Response;
import com.github.seratch.jslack.lightning.response.ResponseUrlSender;
import com.github.seratch.jslack.lightning.util.BuilderConfigurator;
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
