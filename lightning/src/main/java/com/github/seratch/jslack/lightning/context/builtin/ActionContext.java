package com.github.seratch.jslack.lightning.context.builtin;

import com.slack.api.webhook.WebhookResponse;
import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.github.seratch.jslack.lightning.context.Context;
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
public class ActionContext extends Context {

    private String triggerId;
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
