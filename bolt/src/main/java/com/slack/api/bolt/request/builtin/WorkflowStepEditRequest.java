package com.slack.api.bolt.request.builtin;

import com.slack.api.app_backend.interactive_components.payload.WorkflowStepEditPayload;
import com.slack.api.bolt.context.builtin.WorkflowStepEditContext;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.util.json.GsonFactory;
import lombok.ToString;

@ToString(callSuper = true)
// Use new custom steps: https://api.slack.com/automation/functions/custom-bolt
@Deprecated
public class WorkflowStepEditRequest extends Request<WorkflowStepEditContext> {

    private final String requestBody;
    private final RequestHeaders headers;
    private final WorkflowStepEditPayload payload;

    public WorkflowStepEditRequest(
            String requestBody,
            String payloadBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = GsonFactory.createSnakeCase().fromJson(payloadBody, WorkflowStepEditPayload.class);
        if (this.payload != null) {
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
            getContext().setTriggerId(payload.getTriggerId());
            getContext().setCallbackId(payload.getCallbackId());
        }
    }

    private WorkflowStepEditContext context = new WorkflowStepEditContext();

    @Override
    public WorkflowStepEditContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.WorkflowStepEdit;
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
        return null;
    }

    public WorkflowStepEditPayload getPayload() {
        return payload;
    }

}
