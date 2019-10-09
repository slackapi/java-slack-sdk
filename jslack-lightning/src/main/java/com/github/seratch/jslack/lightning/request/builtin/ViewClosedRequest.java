package com.github.seratch.jslack.lightning.request.builtin;

import com.github.seratch.jslack.app_backend.views.payload.ViewClosedPayload;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.github.seratch.jslack.lightning.context.builtin.DefaultContext;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.request.RequestHeaders;
import com.github.seratch.jslack.lightning.request.RequestType;
import lombok.ToString;

@ToString(callSuper = true)
public class ViewClosedRequest extends Request<DefaultContext> {

    private final String requestBody;
    private final RequestHeaders headers;
    private final ViewClosedPayload payload;

    public ViewClosedRequest(
            String requestBody,
            String payloadBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = GsonFactory.createSnakeCase().fromJson(payloadBody, ViewClosedPayload.class);
        getContext().setRequestUserId(payload.getUser().getId());
    }

    private DefaultContext context = new DefaultContext();

    @Override
    public DefaultContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.ViewClosed;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public ViewClosedPayload getPayload() {
        return payload;
    }

}
