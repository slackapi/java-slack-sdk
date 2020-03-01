package com.slack.api.bolt.context;

import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.bolt.response.Responder;
import com.slack.api.bolt.util.BuilderConfigurator;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.webhook.WebhookResponse;

import java.io.IOException;
import java.util.List;

public interface ActionRespondUtility extends RespondUtility {

    default WebhookResponse respond(String text) throws IOException {
        return respond(ActionResponse.builder().text(text).build());
    }

    default WebhookResponse respond(List<LayoutBlock> blocks) throws IOException {
        return respond(ActionResponse.builder().blocks(blocks).build());
    }

    default WebhookResponse respond(ActionResponse response) throws IOException {
        if (getResponder() == null) {
            setResponder(new Responder(getSlack(), getResponseUrl()));
        }
        return getResponder().send(response);
    }

    default WebhookResponse respond(
            BuilderConfigurator<ActionResponse.ActionResponseBuilder> builder) throws IOException {
        return respond(builder.configure(ActionResponse.builder()).build());
    }
}
