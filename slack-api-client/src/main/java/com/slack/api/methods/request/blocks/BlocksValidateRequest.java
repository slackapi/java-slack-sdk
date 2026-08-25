package com.slack.api.methods.request.blocks;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.Attachment;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.view.View;
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
     * A message payload to validate.
     */
    private MessagePayload message;

    /**
     * A message payload to validate, as a JSON-encoded string.
     */
    private String messageAsString;

    /**
     * A view payload to validate.
     */
    private View view;

    /**
     * A view payload to validate, as a JSON-encoded string.
     */
    private String viewAsString;

    /**
     * blocks.validate requires no token or scopes.
     * See https://docs.slack.dev/reference/methods/blocks.validate.
     */
    @Override
    public String getToken() {
        return null;
    }

    @Data
    @Builder
    public static class MessagePayload {
        private List<LayoutBlock> blocks;
        private List<Attachment> attachments;
    }
}
