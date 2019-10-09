package com.github.seratch.jslack.lightning.request.builtin;

import com.github.seratch.jslack.app_backend.interactive_messages.payload.BlockActionPayload;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.github.seratch.jslack.lightning.context.builtin.ActionContext;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.request.RequestHeaders;
import com.github.seratch.jslack.lightning.request.RequestType;
import lombok.ToString;

@ToString(callSuper = true)
public class BlockActionRequest extends Request<ActionContext> {

    private final String requestBody;
    private final RequestHeaders headers;
    private final BlockActionPayload payload;

    public BlockActionRequest(
            String requestBody,
            String payloadBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = GsonFactory.createSnakeCase().fromJson(payloadBody, BlockActionPayload.class);
        getContext().setResponseUrl(payload.getResponseUrl());
        getContext().setTriggerId(payload.getTriggerId());
        getContext().setRequestUserId(payload.getUser().getId());
    }

    private ActionContext context = new ActionContext();

    @Override
    public ActionContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.BlockAction;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public BlockActionPayload getPayload() {
        return payload;
    }

}
