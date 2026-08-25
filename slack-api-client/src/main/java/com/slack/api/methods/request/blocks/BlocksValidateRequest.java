package com.slack.api.methods.request.blocks;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.block.LayoutBlock;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BlocksValidateRequest implements SlackApiRequest {

    /**
     * An array of blocks to validate.
     */
    private List<LayoutBlock> blocks;

    /**
     * An array of blocks to validate, as a JSON-encoded string.
     */
    private String blocksAsString;

    /**
     * A JSON-encoded string of a message payload to validate.
     */
    private String message;

    /**
     * A JSON-encoded string of a view payload to validate.
     */
    private String view;

    /**
     * blocks.validate requires no token or scopes.
     * See https://docs.slack.dev/reference/methods/blocks.validate.
     */
    @Override
    public String getToken() {
        return null;
    }
}
