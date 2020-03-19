package com.slack.api.bolt.request.builtin;

import com.slack.api.app_backend.interactive_components.payload.GlobalShortcutPayload;
import com.slack.api.bolt.context.builtin.GlobalShortcutContext;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.util.json.GsonFactory;
import lombok.ToString;

@ToString(callSuper = true)
public class GlobalShortcutRequest extends Request<GlobalShortcutContext> {

    private final String requestBody;
    private final RequestHeaders headers;
    private final GlobalShortcutPayload payload;

    public GlobalShortcutRequest(
            String requestBody,
            String payloadBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = GsonFactory.createSnakeCase().fromJson(payloadBody, GlobalShortcutPayload.class);

        getContext().setTriggerId(payload.getTriggerId());
        getContext().setEnterpriseId(payload.getTeam().getEnterpriseId());
        getContext().setTeamId(payload.getTeam().getId());
        getContext().setRequestUserId(payload.getUser().getId());
    }

    private GlobalShortcutContext context = new GlobalShortcutContext();

    @Override
    public GlobalShortcutContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.GlobalShortcut;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public GlobalShortcutPayload getPayload() {
        return payload;
    }

    @Override
    public String getResponseUrl() {
        return null;
    }
}
