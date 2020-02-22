package com.slack.api.bolt.context.builtin;

import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.context.SayUtility;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.response.ResponseUrlSender;
import com.slack.api.bolt.util.BuilderConfigurator;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.webhook.WebhookResponse;
import lombok.*;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class SlashCommandContext extends Context implements SayUtility {

    private String triggerId;
    private String channelId;
    private String responseUrl;
    private ResponseUrlSender responseUrlSender;

    public WebhookResponse respond(String text) throws IOException {
        return respond(SlashCommandResponse.builder().text(text).build());
    }

    public WebhookResponse respond(List<LayoutBlock> blocks) throws IOException {
        return respond(SlashCommandResponse.builder().blocks(blocks).build());
    }

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

    public Response ack(String text) {
        return Response.json(200, SlashCommandResponse.builder().text(text).build());
    }

    public Response ack(List<LayoutBlock> blocks) {
        return Response.json(200, SlashCommandResponse.builder().blocks(blocks).build());
    }

    public Response ack(SlashCommandResponse response) {
        return Response.json(200, response);
    }

    public Response ack(
            BuilderConfigurator<SlashCommandResponse.SlashCommandResponseBuilder> builder) {
        return ack(builder.configure(SlashCommandResponse.builder()).build());
    }

}
