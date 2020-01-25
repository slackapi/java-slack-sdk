package com.github.seratch.jslack.lightning.request.builtin;

import com.github.seratch.jslack.app_backend.dialogs.payload.DialogCancellationPayload;
import com.slack.api.util.json.GsonFactory;
import com.github.seratch.jslack.lightning.context.builtin.DialogCancellationContext;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.request.RequestHeaders;
import com.github.seratch.jslack.lightning.request.RequestType;
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
        getContext().setEnterpriseId(payload.getTeam().getEnterpriseId());
        getContext().setTeamId(payload.getTeam().getId());
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

}
