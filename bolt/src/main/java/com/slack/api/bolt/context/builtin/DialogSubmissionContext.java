package com.slack.api.bolt.context.builtin;

import com.slack.api.app_backend.dialogs.response.DialogSubmissionErrorResponse;
import com.slack.api.app_backend.dialogs.response.Error;
import com.slack.api.bolt.context.ActionRespondUtility;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.context.SayUtility;
import com.slack.api.bolt.response.Responder;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.util.BuilderConfigurator;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
public class DialogSubmissionContext extends Context implements SayUtility, ActionRespondUtility {

    private String responseUrl;
    private String channelId;
    private Responder responder;

    public Response ack(List<Error> errors) {
        return ack(DialogSubmissionErrorResponse.builder().errors(errors).build());
    }

    public Response ack(DialogSubmissionErrorResponse error) {
        return ackWithJson(error);
    }

    public Response ack(BuilderConfigurator<DialogSubmissionErrorResponse.DialogSubmissionErrorResponseBuilder> builder) {
        return ackWithJson(builder.configure(DialogSubmissionErrorResponse.builder()).build());
    }

}
