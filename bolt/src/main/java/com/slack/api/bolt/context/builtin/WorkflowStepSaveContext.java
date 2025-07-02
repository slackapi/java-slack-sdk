package com.slack.api.bolt.context.builtin;

import com.slack.api.app_backend.views.response.ViewSubmissionResponse;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.context.WorkflowUpdateUtility;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.util.BuilderConfigurator;
import com.slack.api.model.view.View;
import lombok.*;

import java.util.Map;

/**
 * workflow_step_save type request's context.
 * @deprecated Use new custom steps: https://docs.slack.dev/workflows/workflow-steps
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Deprecated
public class WorkflowStepSaveContext extends Context implements WorkflowUpdateUtility {

    private String workflowStepEditId;

    public Response ack(ViewSubmissionResponse response) {
        return Response.json(200, response);
    }

    public Response ack(
            BuilderConfigurator<ViewSubmissionResponse.ViewSubmissionResponseBuilder> builder) {
        return ack(builder.configure(ViewSubmissionResponse.builder()).build());
    }

    public Response ackWithErrors(Map<String, String> errors) {
        ViewSubmissionResponse response = ViewSubmissionResponse.builder()
                .responseAction("errors")
                .errors(errors)
                .build();
        return ack(response);
    }

    public Response ack(String responseAction, View view) {
        ViewSubmissionResponse response = ViewSubmissionResponse.builder()
                .responseAction(responseAction)
                .view(view)
                .build();
        return ack(response);
    }

    public Response ack(String responseAction, String view) {
        ViewSubmissionResponse response = ViewSubmissionResponse.builder()
                .responseAction(responseAction)
                .viewAsString(view)
                .build();
        return ack(response);
    }
}
