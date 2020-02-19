package com.slack.api.bolt.context.builtin;

import com.slack.api.app_backend.dialogs.response.DialogSuggestionResponse;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.util.BuilderConfigurator;
import lombok.*;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class DialogSuggestionContext extends Context {

    public DialogSuggestionContext() {
    }

    public Response ack(DialogSuggestionResponse response) {
        return Response.json(200, response);
    }

    public Response ack(
            BuilderConfigurator<DialogSuggestionResponse.DialogSuggestionResponseBuilder> builder) {
        return ack(builder.configure(DialogSuggestionResponse.builder()).build());
    }

}
