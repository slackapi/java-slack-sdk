package com.slack.api.bolt.context;

import com.slack.api.Slack;
import com.slack.api.app_backend.views.InputBlockResponseSender;
import com.slack.api.app_backend.views.payload.ViewSubmissionPayload;
import com.slack.api.app_backend.views.response.InputBlockResponse;
import com.slack.api.bolt.util.BuilderConfigurator;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.webhook.WebhookResponse;

import java.io.IOException;
import java.util.List;

/**
 * This utility provides respond method functionality for
 * view_submission request handlers.
 * <p>
 * The `response_urls` in a view_submission payload can exist
 * only when the modal view has an input block with response_url_enabled: true.
 */
public interface InputBlockRespondUtility {

    Slack getSlack();

    InputBlockResponseSender getResponder(String blockId, String actionId);

    InputBlockResponseSender getFirstResponder();

    List<ViewSubmissionPayload.ResponseUrl> getResponseUrls();

    default WebhookResponse respond(String text) throws IOException {
        return respond(InputBlockResponse.builder().text(text).build());
    }

    default WebhookResponse respond(List<LayoutBlock> blocks) throws IOException {
        return respond(InputBlockResponse.builder().blocks(blocks).build());
    }

    default WebhookResponse respond(InputBlockResponse response) throws IOException {
        InputBlockResponseSender responder = getFirstResponder();
        if (responder == null) {
            throw new IllegalStateException("This payload doesn't have response_urls. " +
                    "The response_urls are available only when your modal has input block elements with response_url_enabled = true.");
        }
        return responder.send(response);
    }

    default WebhookResponse respond(
            BuilderConfigurator<InputBlockResponse.InputBlockResponseBuilder> builder) throws IOException {
        return respond(builder.configure(InputBlockResponse.builder()).build());
    }

}
