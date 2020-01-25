package com.github.seratch.jslack.lightning.context.builtin;

import com.slack.api.app_backend.interactive_components.response.BlockSuggestionResponse;
import com.github.seratch.jslack.lightning.context.Context;
import com.github.seratch.jslack.lightning.response.Response;
import com.github.seratch.jslack.lightning.util.BuilderConfigurator;
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
