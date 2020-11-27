package com.slack.api.bolt.request.builtin;

import com.slack.api.app_backend.interactive_components.payload.AttachmentActionPayload;
import com.slack.api.bolt.context.builtin.AttachmentActionContext;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.util.json.GsonFactory;
import lombok.ToString;

@ToString(callSuper = true)
public class AttachmentActionRequest extends Request<AttachmentActionContext> {

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
        if (payload != null) {
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
    }

    private AttachmentActionContext context = new AttachmentActionContext();

    @Override
    public AttachmentActionContext getContext() {
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

    @Override
    public String getResponseUrl() {
        return getPayload() != null ? getPayload().getResponseUrl() : null;
    }

    public AttachmentActionPayload getPayload() {
        return payload;
    }

}
