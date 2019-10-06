package com.github.seratch.jslack.lightning.context.builtin;

import com.github.seratch.jslack.app_backend.outgoing_webhooks.response.WebhookResponse;
import com.github.seratch.jslack.lightning.context.Context;
import com.github.seratch.jslack.lightning.response.Response;
import com.github.seratch.jslack.lightning.util.BuilderConfigurator;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class OutgoingWebhooksContext extends Context {

    private String triggerId;
    private String responseUrl;

    public Response ack(WebhookResponse response) {
        return Response.json(200, response);
    }

    public Response ack(
            BuilderConfigurator<WebhookResponse.WebhookResponseBuilder> builder) {
        return ack(builder.configure(WebhookResponse.builder()).build());
    }

}
