package com.github.seratch.jslack.lightning.request.builtin;

import com.github.seratch.jslack.app_backend.outgoing_webhooks.payload.WebhookPayload;
import com.github.seratch.jslack.app_backend.outgoing_webhooks.payload.WebhookPayloadParser;
import com.github.seratch.jslack.lightning.context.builtin.OutgoingWebhooksContext;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.request.RequestHeaders;
import com.github.seratch.jslack.lightning.request.RequestType;
import lombok.ToString;

@ToString(callSuper = true)
public class OutgoingWebhooksRequest extends Request<OutgoingWebhooksContext> {

    private static final WebhookPayloadParser PAYLOAD_PARSER = new WebhookPayloadParser();

    private final String requestBody;
    private final RequestHeaders headers;
    private final WebhookPayload payload;

    public OutgoingWebhooksRequest(
            String requestBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = PAYLOAD_PARSER.parse(requestBody);
    }

    private OutgoingWebhooksContext context = new OutgoingWebhooksContext();

    @Override
    public OutgoingWebhooksContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.OutgoingWebhooks;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public WebhookPayload getPayload() {
        return payload;
    }
}
