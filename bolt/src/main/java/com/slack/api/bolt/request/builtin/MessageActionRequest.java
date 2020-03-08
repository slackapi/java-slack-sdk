package com.slack.api.bolt.request.builtin;

import com.slack.api.app_backend.interactive_components.payload.MessageActionPayload;
import com.slack.api.bolt.context.builtin.MessageActionContext;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.util.json.GsonFactory;
import lombok.ToString;

@ToString(callSuper = true)
public class MessageActionRequest extends Request<MessageActionContext> {

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
        if (payload.getChannel() != null) {
            getContext().setChannelId(payload.getChannel().getId());
        }
        getContext().setRequestUserId(payload.getUser().getId());
    }

    private MessageActionContext context = new MessageActionContext();

    @Override
    public MessageActionContext getContext() {
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

    @Override
    public String getResponseUrl() {
        return getPayload() != null ? getPayload().getResponseUrl() : null;
    }
}
