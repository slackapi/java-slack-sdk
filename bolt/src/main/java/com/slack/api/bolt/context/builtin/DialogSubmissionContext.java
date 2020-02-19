package com.slack.api.bolt.context.builtin;

import com.slack.api.app_backend.dialogs.response.DialogSubmissionErrorResponse;
import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.context.SayUtility;
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
public class DialogSubmissionContext extends Context implements SayUtility {

    private String responseUrl;
    private String channelId;
    private ResponseUrlSender responseUrlSender;

    public WebhookResponse respond(ActionResponse response) throws IOException {
        if (responseUrlSender == null) {
            responseUrlSender = new ResponseUrlSender(slack, responseUrl);
        }
        return responseUrlSender.send(response);
    }

    public WebhookResponse respond(
            BuilderConfigurator<ActionResponse.ActionResponseBuilder> builder) throws IOException {
        return respond(builder.configure(ActionResponse.builder()).build());
    }

    public Response ack(DialogSubmissionErrorResponse error) {
        return ackWithJson(error);
    }

    public Response ack(BuilderConfigurator<DialogSubmissionErrorResponse.DialogSubmissionErrorResponseBuilder> builder) {
        return ackWithJson(builder.configure(DialogSubmissionErrorResponse.builder()).build());
    }

}
