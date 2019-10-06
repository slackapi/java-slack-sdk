package com.github.seratch.jslack.lightning.context.builtin;

import com.github.seratch.jslack.app_backend.dialogs.response.DialogSuggestionResponse;
import com.github.seratch.jslack.lightning.context.Context;
import com.github.seratch.jslack.lightning.response.Response;
import com.github.seratch.jslack.lightning.util.BuilderConfigurator;
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
