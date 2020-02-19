package com.slack.api.bolt.context.builtin;

import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.context.SayUtility;
import com.slack.api.bolt.response.ResponseUrlSender;
import com.slack.api.bolt.util.BuilderConfigurator;
import com.slack.api.webhook.WebhookResponse;
import lombok.*;

import java.io.IOException;

/**
 * Action type request's context from attachments in messages.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class AttachmentActionContext extends Context implements SayUtility {

    private String triggerId;
    private String channelId;
    private String responseUrl;
    private ResponseUrlSender responseUrlSender;

    public WebhookResponse respond(ActionResponse response) throws IOException {
        if (responseUrlSender == null) {
            responseUrlSender = new ResponseUrlSender(slack, responseUrl);
        }
        return new ResponseUrlSender(slack, responseUrl).send(response);
    }

    public WebhookResponse respond(
            BuilderConfigurator<ActionResponse.ActionResponseBuilder> builder) throws IOException {
        return respond(builder.configure(ActionResponse.builder()).build());
    }

}
