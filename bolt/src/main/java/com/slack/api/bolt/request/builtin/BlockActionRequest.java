package com.slack.api.bolt.request.builtin;

import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.util.json.GsonFactory;
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
        if (this.payload != null) {
            getContext().setResponseUrl(payload.getResponseUrl());
            getContext().setTriggerId(payload.getTriggerId());
            if (payload.getEnterprise() != null) {
                getContext().setEnterpriseId(payload.getEnterprise().getId());
            } else if (payload.getTeam() != null) {
                getContext().setEnterpriseId(payload.getTeam().getEnterpriseId());
            }
            if (payload.getTeam() != null && payload.getTeam().getId() != null) {
                getContext().setTeamId(payload.getTeam().getId());
            } else if (payload.getUser() != null && payload.getUser().getTeamId() != null) {
                getContext().setTeamId(payload.getUser().getTeamId());
            }
            getContext().setRequestUserId(payload.getUser().getId());
        }
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

    @Override
    public String getResponseUrl() {
        return getPayload() != null ? getPayload().getResponseUrl() : null;
    }
}
