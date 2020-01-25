package com.github.seratch.jslack.lightning.request.builtin;

import com.github.seratch.jslack.app_backend.interactive_messages.payload.AttachmentActionPayload;
import com.slack.api.util.json.GsonFactory;
import com.github.seratch.jslack.lightning.context.builtin.ActionContext;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.request.RequestHeaders;
import com.github.seratch.jslack.lightning.request.RequestType;
import lombok.ToString;

@ToString(callSuper = true)
public class AttachmentActionRequest extends Request<ActionContext> {

    private final String requestBody;
    private final RequestHeaders headers;
    private final AttachmentActionPayload payload;

    public AttachmentActionRequest(
            String requestBody,
            String payloadBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = GsonFactory.createSnakeCase().fromJson(payloadBody, AttachmentActionPayload.class);
        getContext().setResponseUrl(payload.getResponseUrl());
        getContext().setEnterpriseId(payload.getTeam().getEnterpriseId());
        getContext().setTeamId(payload.getTeam().getId());
        getContext().setRequestUserId(payload.getUser().getId());
    }

    private ActionContext context = new ActionContext();

    @Override
    public ActionContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.AttachmentAction;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public AttachmentActionPayload getPayload() {
        return payload;
    }

}
