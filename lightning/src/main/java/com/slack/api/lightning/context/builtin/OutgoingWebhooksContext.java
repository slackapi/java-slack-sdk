package com.slack.api.lightning.context.builtin;

import com.slack.api.app_backend.outgoing_webhooks.response.WebhookResponse;
import com.slack.api.lightning.context.Context;
import com.slack.api.lightning.response.Response;
import com.slack.api.lightning.util.BuilderConfigurator;
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
