package com.slack.api.lightning.request.builtin;

import com.slack.api.app_backend.views.payload.ViewClosedPayload;
import com.slack.api.lightning.context.builtin.DefaultContext;
import com.slack.api.lightning.request.Request;
import com.slack.api.lightning.request.RequestHeaders;
import com.slack.api.lightning.request.RequestType;
import com.slack.api.util.json.GsonFactory;
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

        getContext().setEnterpriseId(payload.getTeam().getEnterpriseId());
        getContext().setTeamId(payload.getTeam().getId());
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
