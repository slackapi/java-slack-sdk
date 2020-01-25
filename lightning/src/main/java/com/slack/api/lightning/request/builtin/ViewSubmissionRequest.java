package com.slack.api.lightning.request.builtin;

import com.slack.api.app_backend.views.payload.ViewSubmissionPayload;
import com.slack.api.lightning.context.builtin.ViewSubmissionContext;
import com.slack.api.lightning.request.Request;
import com.slack.api.lightning.request.RequestHeaders;
import com.slack.api.lightning.request.RequestType;
import com.slack.api.util.json.GsonFactory;
import lombok.ToString;

@ToString(callSuper = true)
public class ViewSubmissionRequest extends Request<ViewSubmissionContext> {

    private final String requestBody;
    private final RequestHeaders headers;
    private final ViewSubmissionPayload payload;

    public ViewSubmissionRequest(
            String requestBody,
            String payloadBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = GsonFactory.createSnakeCase().fromJson(payloadBody, ViewSubmissionPayload.class);

        getContext().setEnterpriseId(payload.getTeam().getEnterpriseId());
        getContext().setTeamId(payload.getTeam().getId());
        getContext().setRequestUserId(payload.getUser().getId());
    }

    private ViewSubmissionContext context = new ViewSubmissionContext();

    @Override
    public ViewSubmissionContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.ViewSubmission;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public ViewSubmissionPayload getPayload() {
        return payload;
    }

}
