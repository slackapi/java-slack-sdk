package com.slack.api.lightning.context.builtin;

import com.slack.api.app_backend.views.response.ViewSubmissionResponse;
import com.slack.api.lightning.context.Context;
import com.slack.api.lightning.response.Response;
import com.slack.api.lightning.util.BuilderConfigurator;
import lombok.*;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ViewSubmissionContext extends Context {

    public ViewSubmissionContext() {
    }

    public Response ack(ViewSubmissionResponse response) {
        return Response.json(200, response);
    }

    public Response ack(
            BuilderConfigurator<ViewSubmissionResponse.ViewSubmissionResponseBuilder> builder) {
        return ack(builder.configure(ViewSubmissionResponse.builder()).build());
    }

}
