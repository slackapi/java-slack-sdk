package com.slack.api.bolt.request.builtin;

import com.slack.api.app_backend.dialogs.payload.DialogCancellationPayload;
import com.slack.api.bolt.context.builtin.DialogCancellationContext;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.util.json.GsonFactory;
import lombok.ToString;

@ToString(callSuper = true)
public class DialogCancellationRequest extends Request<DialogCancellationContext> {

    private final String requestBody;
    private final RequestHeaders headers;
    private final DialogCancellationPayload payload;

    public DialogCancellationRequest(
            String requestBody,
            String payloadBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = GsonFactory.createSnakeCase().fromJson(payloadBody, DialogCancellationPayload.class);
        getContext().setResponseUrl(payload.getResponseUrl());
        if (payload.getEnterprise() != null) {
            getContext().setEnterpriseId(payload.getEnterprise().getId());
        } else if (payload.getTeam() != null) {
            getContext().setEnterpriseId(payload.getTeam().getEnterpriseId());
        }
        if (payload.getTeam() != null) {
            getContext().setTeamId(payload.getTeam().getId());
        }
        if (payload.getChannel() != null) {
            getContext().setChannelId(payload.getChannel().getId());
        }
        getContext().setRequestUserId(payload.getUser().getId());
    }

    private DialogCancellationContext context = new DialogCancellationContext();

    @Override
    public DialogCancellationContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.DialogCancellation;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public DialogCancellationPayload getPayload() {
        return payload;
    }

    @Override
    public String getResponseUrl() {
        return getPayload() != null ? getPayload().getResponseUrl() : null;
    }
}
