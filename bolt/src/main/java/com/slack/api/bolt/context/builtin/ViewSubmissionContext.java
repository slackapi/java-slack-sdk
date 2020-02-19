package com.slack.api.bolt.context.builtin;

import com.slack.api.app_backend.views.response.ViewSubmissionResponse;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.util.BuilderConfigurator;
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
