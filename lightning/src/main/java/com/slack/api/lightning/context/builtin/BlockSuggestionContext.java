package com.slack.api.lightning.context.builtin;

import com.slack.api.app_backend.interactive_components.response.BlockSuggestionResponse;
import com.slack.api.lightning.context.Context;
import com.slack.api.lightning.response.Response;
import com.slack.api.lightning.util.BuilderConfigurator;
import lombok.*;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class BlockSuggestionContext extends Context {

    public BlockSuggestionContext() {
    }

    public Response ack(BlockSuggestionResponse response) {
        return Response.json(200, response);
    }

    public Response ack(
            BuilderConfigurator<BlockSuggestionResponse.BlockSuggestionResponseBuilder> builder) {
        return ack(builder.configure(BlockSuggestionResponse.builder()).build());
    }

}
