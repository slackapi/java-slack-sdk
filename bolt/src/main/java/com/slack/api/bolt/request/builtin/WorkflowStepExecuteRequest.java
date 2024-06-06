package com.slack.api.bolt.request.builtin;

import com.google.gson.Gson;
import com.slack.api.app_backend.events.payload.WorkflowStepExecutePayload;
import com.slack.api.bolt.context.builtin.WorkflowStepExecuteContext;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.util.json.GsonFactory;
import lombok.ToString;

@ToString(callSuper = true)
// Use new custom steps: https://api.slack.com/automation/functions/custom-bolt
@Deprecated
public class WorkflowStepExecuteRequest extends Request<WorkflowStepExecuteContext> {

    private final String requestBody;
    private final RequestHeaders headers;
    private final WorkflowStepExecutePayload payload;

    private static final Gson GSON = GsonFactory.createSnakeCase();

    public WorkflowStepExecuteRequest(
            String requestBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = GSON.fromJson(requestBody, WorkflowStepExecutePayload.class);
        if (this.payload != null) {
            if (payload.getAuthorizations() != null && payload.getAuthorizations().size() > 0) {
                getContext().setEnterpriseId(payload.getAuthorizations().get(0).getEnterpriseId());
                getContext().setTeamId(payload.getAuthorizations().get(0).getTeamId());
            } else {
                getContext().setEnterpriseId(payload.getEnterpriseId());
                getContext().setTeamId(payload.getTeamId());
            }
            getContext().setRequestUserId(null);
            getContext().setCallbackId(payload.getEvent().getCallbackId());
            getContext().setWorkflowStepExecuteId(payload.getEvent().getWorkflowStep().getWorkflowStepExecuteId());
        }
    }

    private WorkflowStepExecuteContext context = new WorkflowStepExecuteContext();

    @Override
    public WorkflowStepExecuteContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.WorkflowStepExecute;
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

    public WorkflowStepExecutePayload getPayload() {
        return this.payload;
    }

}
