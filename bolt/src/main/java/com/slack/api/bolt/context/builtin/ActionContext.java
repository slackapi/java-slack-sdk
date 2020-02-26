package com.slack.api.bolt.context.builtin;

import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.response.Responder;
import com.slack.api.bolt.util.BuilderConfigurator;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.webhook.WebhookResponse;
import lombok.*;

import java.io.IOException;
import java.util.List;

/**
 * Action type request's context.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ActionContext extends Context {

    private String triggerId;
    private String responseUrl;
    private Responder responder;

    public WebhookResponse respond(String text) throws IOException {
        return respond(ActionResponse.builder().text(text).build());
    }

    public WebhookResponse respond(List<LayoutBlock> blocks) throws IOException {
        return respond(ActionResponse.builder().blocks(blocks).build());
    }

    public WebhookResponse respond(ActionResponse response) throws IOException {
        if (responder == null) {
            responder = new Responder(slack, responseUrl);
        }
        return responder.send(response);
    }

    public WebhookResponse respond(
            BuilderConfigurator<ActionResponse.ActionResponseBuilder> builder) throws IOException {
        return respond(builder.configure(ActionResponse.builder()).build());
    }

}
