package com.github.seratch.jslack.lightning.request.builtin;

import com.slack.api.app_backend.interactive_components.payload.MessageActionPayload;
import com.slack.api.util.json.GsonFactory;
import com.github.seratch.jslack.lightning.context.builtin.ActionContext;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.request.RequestHeaders;
import com.github.seratch.jslack.lightning.request.RequestType;
import lombok.ToString;

@ToString(callSuper = true)
public class MessageActionRequest extends Request<ActionContext> {

    private final String requestBody;
    private final RequestHeaders headers;
    private final MessageActionPayload payload;

    public MessageActionRequest(
            String requestBody,
            String payloadBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = GsonFactory.createSnakeCase().fromJson(payloadBody, MessageActionPayload.class);

        getContext().setResponseUrl(payload.getResponseUrl());
        getContext().setTriggerId(payload.getTriggerId());
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
        return RequestType.MessageAction;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public MessageActionPayload getPayload() {
        return payload;
    }

}
