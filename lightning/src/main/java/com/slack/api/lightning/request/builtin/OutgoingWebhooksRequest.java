package com.slack.api.lightning.request.builtin;

import com.slack.api.app_backend.outgoing_webhooks.WebhookPayloadParser;
import com.slack.api.app_backend.outgoing_webhooks.payload.WebhookPayload;
import com.slack.api.lightning.context.builtin.OutgoingWebhooksContext;
import com.slack.api.lightning.request.Request;
import com.slack.api.lightning.request.RequestHeaders;
import com.slack.api.lightning.request.RequestType;
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
        getContext().setRequestUserId(payload.getUserId());
        getContext().setTeamId(payload.getTeamId());
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
