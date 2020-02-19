package com.slack.api.bolt.request.builtin;

import com.slack.api.app_backend.dialogs.payload.DialogSubmissionPayload;
import com.slack.api.bolt.context.builtin.DialogSubmissionContext;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.util.json.GsonFactory;
import lombok.ToString;

@ToString(callSuper = true)
public class DialogSubmissionRequest extends Request<DialogSubmissionContext> {

    private final String requestBody;
    private final RequestHeaders headers;
    private final DialogSubmissionPayload payload;

    public DialogSubmissionRequest(
            String requestBody,
            String payloadBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = GsonFactory.createSnakeCase().fromJson(payloadBody, DialogSubmissionPayload.class);
        getContext().setResponseUrl(payload.getResponseUrl());
        getContext().setEnterpriseId(payload.getTeam().getEnterpriseId());
        getContext().setTeamId(payload.getTeam().getId());
        if (payload.getChannel() != null) {
            getContext().setChannelId(payload.getChannel().getId());
        }
        getContext().setRequestUserId(payload.getUser().getId());
    }

    private DialogSubmissionContext context = new DialogSubmissionContext();

    @Override
    public DialogSubmissionContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.DialogSubmission;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public DialogSubmissionPayload getPayload() {
        return payload;
    }

}
