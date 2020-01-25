package com.slack.api.lightning.context.builtin;

import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.lightning.context.Context;
import com.slack.api.lightning.response.ResponseUrlSender;
import com.slack.api.lightning.util.BuilderConfigurator;
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
public class DialogCancellationContext extends Context {

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
